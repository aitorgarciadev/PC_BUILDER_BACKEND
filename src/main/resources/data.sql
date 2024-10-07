-- Users
INSERT INTO users (user_id, email, password, role) VALUES (default, 'user@gmail.com', '$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO', 'USER');
INSERT INTO users (user_id, email, password, role) VALUES (default, 'admin@gmail.com', '$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO', 'ADMIN');

-- Profiles
INSERT INTO profiles (first_name, last_name, phone_number, street, city, region, postal_code, country, user_id, is_shipping, is_subscribed) VALUES
('Lucas', 'Hernández', '5557654321', '789 Calle Nueva', 'Monterrey', 'Nuevo León', '64000', 'México', 1, false, true),
('Valentina', 'García', '5551239876', '321 Calle Vieja', 'Puebla', 'Puebla', '72000', 'México', 2, true, false);

-- Categories
INSERT INTO categories (category_id, name, highlights, image_hash) VALUES 
(default, 'Motherboard', false, 'http://localhost:5173/src/assets/img/Carrousel/motherboard.jpg'),
(default, 'CPU', true, 'http://localhost:5173/src/assets/img/Carrousel/cpu.jpg'),
(default, 'GPU', true, 'http://localhost:5173/src/assets/img/Carrousel/gpu.jpg'),
(default, 'RAM', false, 'http://localhost:5173/src/assets/img/Carrousel/ram.jpg'),
(default, 'Storage', true, 'http://localhost:5173/src/assets/img/Carrousel/storage.jpg'),
(default, 'Power Supply', false, 'http://localhost:5173/src/assets/img/Carrousel/power_supply.jpg'),
(default, 'Cooling System', true, 'http://localhost:5173/src/assets/img/Carrousel/cooling.jpg'),
(default, 'Case', false, 'http://localhost:5173/src/assets/img/Carrousel/case.jpg');

-- Additional Products
INSERT INTO products (product_id, name, description, price, stock, category_id, discount, created_at) VALUES
(default, 'ASUS ROG Strix B550-F', 'High-performance motherboard with robust features.', 199.99, 25, 1, 10, '2024-01-01 10:00:00'),
(default, 'Intel Core i9-11900K', 'Powerful 11th Gen Intel Core processor.', 499.99, 15, 2, 0, '2024-02-01 11:30:00'),
(default, 'NVIDIA GeForce RTX 3080', 'High-end graphics card for gamers.', 799.99, 10, 3, 15, '2024-03-01 12:00:00'),
(default, 'Corsair Vengeance LPX 16GB', '16GB RAM module for fast performance.', 79.99, 50, 4, 20, '2024-01-15 09:00:00'),
(default, 'Samsung 970 EVO 1TB', '1TB NVMe SSD for quick data access.', 129.99, 30, 5, 0, '2024-01-10 14:00:00'),
(default, 'EVGA 600 W1 80+', '600W power supply with great reliability.', 49.99, 60, 6, 10, '2024-01-20 16:45:00'),
(default, 'Cooler Master Hyper 212', 'Air cooler for effective CPU cooling.', 39.99, 35, 7, 5, '2024-02-05 13:15:00'),
(default, 'NZXT H510', 'Stylish mid-tower case with good airflow.', 69.99, 25, 8, 15, '2024-01-25 11:45:00'),
(default, 'AMD Ryzen 7 5800X', 'High-performance AMD processor.', 399.99, 20, 2, 0, '2024-03-05 15:00:00'),
(default, 'G.Skill Ripjaws V 32GB', '32GB RAM for demanding applications.', 139.99, 15, 4, 10, '2024-03-10 11:30:00'),
(default, 'Western Digital Blue 2TB', '2TB HDD for large storage needs.', 54.99, 40, 5, 0, '2024-02-20 17:20:00'),
(default, 'Corsair RM750x', '750W power supply for high-performance PCs.', 129.99, 10, 6, 20, '2024-02-01 12:10:00'),
(default, 'Pure Rock 2', 'CPU cooler for silent operation.', 49.99, 20, 7, 5, '2024-02-25 09:15:00'),
(default, 'Thermaltake V200', 'RGB mid-tower case with tempered glass.', 89.99, 25, 8, 10, '2024-02-10 13:50:00'),
(default, 'MSI Gaming X Trio RTX 3070', 'Powerful graphics card for gaming.', 599.99, 5, 3, 0, '2024-03-01 08:45:00'),
(default, 'ASRock B450M Pro4', 'Affordable motherboard with essential features.', 89.99, 40, 1, 15, '2024-01-05 10:10:00'),
(default, 'HyperX Fury 8GB', '8GB RAM module for basic tasks.', 39.99, 60, 4, 0, '2024-01-15 14:50:00'),
(default, 'Crucial MX500 500GB', '500GB SSD for quick boot times.', 59.99, 30, 5, 20, '2024-02-18 10:20:00');

-- Orders
INSERT INTO orders (order_id, status, total_price, total_items, is_paid, user_id) VALUES
(default, 'PENDING', 299.99, 1, true, 1),
(default, 'PENDING', 629.99, 3, true, 2);

-- OrderItems
INSERT INTO order_items (order_item_id, quantity, order_id, product_id) VALUES (default, 1, 1, 1);
INSERT INTO order_items (order_item_id, quantity, order_id, product_id) VALUES (default, 2, 1, 3);

-- Reviews
INSERT INTO reviews (review_id, rating, order_item_id, user_id) VALUES (default, 5, 1, 1);
INSERT INTO reviews (review_id, rating, order_item_id, user_id) VALUES (default, 4, 2, 1);
