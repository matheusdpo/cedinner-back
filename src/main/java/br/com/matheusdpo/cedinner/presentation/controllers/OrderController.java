package br.com.matheusdpo.cedinner.presentation.controllers;

import br.com.matheusdpo.cedinner.application.dto.CreateOrderRequest;
import br.com.matheusdpo.cedinner.application.dto.OrderResponse;
import br.com.matheusdpo.cedinner.application.dto.UpdateOrderRequest;
import br.com.matheusdpo.cedinner.application.usecases.CreateOrderUseCase;
import br.com.matheusdpo.cedinner.application.usecases.DeleteOrderUseCase;
import br.com.matheusdpo.cedinner.application.usecases.GetOrderByIdUseCase;
import br.com.matheusdpo.cedinner.application.usecases.GetOrdersByUserUseCase;
import br.com.matheusdpo.cedinner.application.usecases.UpdateOrderUseCase;
import br.com.matheusdpo.cedinner.presentation.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final GetOrdersByUserUseCase getOrdersByUserUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                          GetOrderByIdUseCase getOrderByIdUseCase,
                          GetOrdersByUserUseCase getOrdersByUserUseCase,
                          UpdateOrderUseCase updateOrderUseCase,
                          DeleteOrderUseCase deleteOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.getOrdersByUserUseCase = getOrdersByUserUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.deleteOrderUseCase = deleteOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        try {
            OrderResponse order = createOrderUseCase.execute(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(order, "Order created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An unexpected error occurred"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long id) {
        try {
            OrderResponse order = getOrderByIdUseCase.execute(id);
            return ResponseEntity.ok(ApiResponse.success(order, "Order retrieved successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An unexpected error occurred"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUser(@PathVariable Long userId) {
        try {
            List<OrderResponse> orders = getOrdersByUserUseCase.execute(userId);
            return ResponseEntity.ok(ApiResponse.success(orders, "Orders retrieved successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An unexpected error occurred"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(@PathVariable Long id, 
                                                                 @Valid @RequestBody UpdateOrderRequest request) {
        try {
            OrderResponse order = updateOrderUseCase.execute(id, request);
            return ResponseEntity.ok(ApiResponse.success(order, "Order updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An unexpected error occurred"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
        try {
            deleteOrderUseCase.execute(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Order deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An unexpected error occurred"));
        }
    }
}
