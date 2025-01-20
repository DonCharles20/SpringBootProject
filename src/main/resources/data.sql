CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price NUMERIC,
    stock_quantity INT,
    category VARCHAR(255),
    brand VARCHAR(255),
    release_date DATE,
    product_available BOOLEAN,
    imagename VARCHAR(255),
    image_data BYTEA,  
    image_type VARCHAR(255)
);
