CREATE TABLE foods (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    calories_per_100g INTEGER NOT NULL,
    protein_per_100g DECIMAL(6, 2) DEFAULT NULL,
    carbs_per_100g DECIMAL(6, 2) DEFAULT NULL,
    fats_per_100g DECIMAL(6, 2) DEFAULT NULL,
    serving_size_g DECIMAL(6, 2) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    barcode BIGINT DEFAULT NULL
);