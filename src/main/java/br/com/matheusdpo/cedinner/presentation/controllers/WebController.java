package br.com.matheusdpo.cedinner.presentation.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.matheusdpo.cedinner.application.dto.CreateOrderRequest;
import br.com.matheusdpo.cedinner.application.dto.OrderResponse;
import br.com.matheusdpo.cedinner.application.dto.RegisterUserRequest;
import br.com.matheusdpo.cedinner.application.dto.UpdateOrderRequest;
import br.com.matheusdpo.cedinner.application.usecases.CreateOrderUseCase;
import br.com.matheusdpo.cedinner.application.usecases.DeleteOrderUseCase;
import br.com.matheusdpo.cedinner.application.usecases.GetAllOrdersUseCase;
import br.com.matheusdpo.cedinner.application.usecases.GetOrderByIdUseCase;
import br.com.matheusdpo.cedinner.application.usecases.GetOrdersByUserUseCase;
import br.com.matheusdpo.cedinner.application.usecases.RegisterUserUseCase;
import br.com.matheusdpo.cedinner.application.usecases.UpdateOrderUseCase;
import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.ProductJpaEntity;
import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.UserJpaEntity;
import br.com.matheusdpo.cedinner.infrastructure.persistence.repositories.ProductJpaRepository;
import br.com.matheusdpo.cedinner.infrastructure.persistence.repositories.UserJpaRepository;
import jakarta.validation.Valid;

@Controller
public class WebController {

    private final RegisterUserUseCase registerUserUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrdersByUserUseCase getOrdersByUserUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final UserJpaRepository userRepository;
    private final ProductJpaRepository productRepository;

    public WebController(RegisterUserUseCase registerUserUseCase,
                        CreateOrderUseCase createOrderUseCase,
                        GetOrdersByUserUseCase getOrdersByUserUseCase,
                        GetOrderByIdUseCase getOrderByIdUseCase,
                        UpdateOrderUseCase updateOrderUseCase,
                        DeleteOrderUseCase deleteOrderUseCase,
                        GetAllOrdersUseCase getAllOrdersUseCase,
                        UserJpaRepository userRepository,
                        ProductJpaRepository productRepository) {
        this.registerUserUseCase = registerUserUseCase;
        this.createOrderUseCase = createOrderUseCase;
        this.getOrdersByUserUseCase = getOrdersByUserUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.deleteOrderUseCase = deleteOrderUseCase;
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegisterUserRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") RegisterUserRequest request,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            registerUserUseCase.execute(request);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/orders")
    public String listOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        UserJpaEntity user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderResponse> orders = getOrdersByUserUseCase.execute(user.getId());
        model.addAttribute("orders", orders);
        model.addAttribute("username", user.getName());
        
        return "orders";
    }

    @GetMapping("/orders/new")
    public String showCreateOrderForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        UserJpaEntity user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ProductJpaEntity> products = productRepository.findAll();
        
        model.addAttribute("order", new CreateOrderRequest());
        model.addAttribute("products", products);
        model.addAttribute("userId", user.getId());
        model.addAttribute("username", user.getName());
        model.addAttribute("userAddress", user.getAddress() != null ? user.getAddress() : "");
        
        return "create-order-modern";
    }

    @PostMapping("/orders/create")
    public String createOrder(@Valid @ModelAttribute("order") CreateOrderRequest request,
                             BindingResult result,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please fill all required fields");
            return "redirect:/orders/new";
        }

        try {
            createOrderUseCase.execute(request);
            redirectAttributes.addFlashAttribute("success", "Order created successfully!");
            return "redirect:/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/orders/new";
        }
    }

    @GetMapping("/orders/{id}/edit")
    public String showEditOrderForm(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        UserJpaEntity user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        OrderResponse order = getOrderByIdUseCase.execute(id);
        
        // Check if order belongs to user
        if (!order.getUserId().equals(user.getId())) {
            return "redirect:/orders";
        }

        List<ProductJpaEntity> products = productRepository.findAll();
        
        model.addAttribute("order", order);
        model.addAttribute("products", products);
        model.addAttribute("username", user.getName());
        
        return "edit-order";
    }

    @PostMapping("/orders/{id}/update")
    public String updateOrder(@PathVariable Long id,
                             @ModelAttribute UpdateOrderRequest request,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            updateOrderUseCase.execute(id, request);
            redirectAttributes.addFlashAttribute("success", "Order updated successfully!");
            return "redirect:/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/orders/" + id + "/edit";
        }
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable Long id,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            deleteOrderUseCase.execute(id);
            redirectAttributes.addFlashAttribute("success", "Order deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/orders";
    }

    @GetMapping("/orders/admin/allOrders")
    public String getAllOrders(Model model) {
        List<OrderResponse> orders = getAllOrdersUseCase.execute();
        model.addAttribute("orders", orders);
        return "admin-orders";
    }
}

