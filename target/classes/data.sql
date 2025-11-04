-- Delete existing products if any
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM products;

-- Insert hamburgers, sides and drinks
-- Images will be loaded from /images/products/ folder

-- ========== HAMBURGUERES ==========
INSERT INTO products (name, description, price, available, category, image_url, addable_items, removable_items) VALUES
('Amborgue Classic', 'Pao com Gergelim selado na manteiga, Uma Carne Bovina 80g Grelhada, Alface americano, Cebola, Molho do Big Tasty, Bacon em cubinhos bem piticos, Uma fatia de queijo tipo cheddar', 29.90, true, 'Hamburguer',
'/images/products/1.jpg',
'Substituir Carne 120g,Carne (80g),Carne (120g),Queijo Mussarela,Queijo cheddar,Bacon,Ovos,Tomate,Mostarda,Maionese,Katchup', 'Alface,Cebola,Molho,Bacon,Tomate,Queijo'),

('Cheddar Classic', 'Pao tipo Brioche selado na manteiga, Uma Carne Bovina 80g Grelhada, Cebola caramelizada, Cheddar cremoso', 24.90, true, 'Hamburguer',
'/images/products/2.jpg',
'Substituir Carne 120g,Carne (80g),Carne (120g),Extra cheddar,Bacon,Mostarda,Maionese,Katchup', 'Cebola'),

('ULTRA AMBORGUE CHEDDAR CLASSIC', 'INCLUSO TUDO!! Pao tipo Brioche selado na manteiga, DUAS Carne Bovina 120g Grelhada, Cebola, Cebola caramelizada, Molho do Big Tasty,Cheddar cremoso, Alface Americano, Bacon em cubinhos bem piticos', 49.90, true, 'Hamburguer',
'/images/products/3.jpg',
'Substituir Carne 80g,Extra cheddar,Bacon,Mostarda,Maionese,Katchup', 'Alface,Cebola,Molho,Bacon,Tomate,Queijo'),

-- ========== ACOMPANHAMENTOS ==========
('Batata Frita Tradicional', 'Porção generosa de batatas fritas crocantes por fora e macias por dentro, temperadas na medida certa com sal especial. Acompanha molho à sua escolha', 24.90, true, 'Acompanhamento',
'/images/products/4.jpg',
'Queijo cheddar,Quejo Mussarela,Bacon', 'Sal'),

('Onion Rings (Cebola Empanada)', 'Deliciosos anéis de cebola empanados em uma massa crocante especial, fritos até ficarem dourados. Servidos com molho barbecue defumado para mergulhar', 19.90, true, 'Acompanhamento',
'/images/products/5.jpg',
'', ''),

-- ========== BEBIDAS - ENERGÉTICOS ==========
('Red Bull 250ml', 'Red Bull Energy Drink - Bebida energética premium que te dá asas. Lata 250ml geladinha no ponto', 14.90, true, 'Bebida - Energy',
'/images/products/6.jpg',
'Red Bull,Red Bull Sugarfree,Red Bull Zero,Tropical,Melancia,Pêssego,Melão (Maracujá & Melão),Cereja,Winter Edition,Amora (Sugarfree),Pomelo (Sugarfree),Red Edition (Watermelon),Blue Edition (Blueberry),Gelo', ''),

('Red Bull 473ml', 'Red Bull Energy Drink - Bebida energética premium que te dá asas. Lata 473ml geladinha no ponto', 19.90, true, 'Bebida - Energy',
'/images/products/6.jpg',
'Red Bull,Red Bull Zero,Gelo', ''),

('Monster Energy', 'Monster Energy Green - A bebida energética com o sabor marcante que você adora. Lata 473ml bem gelada', 19.90, true, 'Bebida - Energy',
'/images/products/7.jpg',
'Original,Original Sugar Free, Branco Sugar Free, Maca Verde, Mango Loko, Mango Loko Sugar Free,Green Tea,Inserir na Observacao,Gelo', ''),

-- ========== BEBIDAS - ALCOÓLICAS ==========
('Original Lata 350ml', 'Cerveja Original - Sabor único e refrescante. Filha-da-putamente no ponto', 6.90, true, 'Bebida - Alcoólica',
'/images/products/8.jpg',
'', 'Metanol'),

('Original Garrafa 600ml', 'Cerveja Original - Sabor único e refrescante. Filha-da-putamente no ponto', 19.90, true, 'Bebida - Alcoólica',
'/images/products/8.jpg',
'', 'Metanol'),

('Eisenbahn Lata 350ml', 'Heil Eisenbahn! A melhor que ta tendo na alemanha, desde 1945. Estupidamente no ponto.', 7.90, true, 'Bebida - Alcoólica',
'/images/products/9.jpg',
'', 'Metanol'),

('Heineken Lata 350ml', 'Heineken - A cerveja da champions league. Estupidamente no ponto.', 9.90, true, 'Bebida - Alcoólica',
'/images/products/10.jpg',
'', 'Metanol'),

('Heineken Garrafa 600ml', 'Heineken - A cerveja da champions league. Estupidamente no ponto.', 22.90, true, 'Bebida - Alcoólica',
'/images/products/10.jpg',
'', 'Metanol'),

-- ========== BEBIDAS - REFRIGERANTES ==========
('Coca-Cola Espumante - Normar Lata 350ml', 'Coca-Cola Espumante, pica de jegue no cu do elefante', 9.49, true, 'Bebida - Refrigerante',
'/images/products/11.jpg',
'Gelo,Limão', 'Rato'),

('Coca-Cola Espumante - Normar Garrafa 600ml', 'Coca-Cola Espumante, pica de jegue no cu do elefante', 13.49, true, 'Bebida - Refrigerante',
'/images/products/11.jpg',
'Gelo,Limão', 'Rato'),

('Coca-Cola Espumante - Zero Lata 350ml', 'Coca-Cola Espumante, pica de jegue no cu do elefante', 9.49, true, 'Bebida - Refrigerante',
'/images/products/12.jpg',
'Gelo,Limão', 'Rato'),

('Coca-Cola Espumante - Zero Garrafa 600ml', 'Coca-Cola Espumante, pica de jegue no cu do elefante', 13.49, true, 'Bebida - Refrigerante',
'/images/products/12.jpg',
'Gelo,Limão', 'Rato');