CREATE TABLE food_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    food_id UUID REFERENCES foods(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    calories INTEGER NOT NULL,
    protein_g DECIMAL(6, 2),
    carbs_g DECIMAL(6, 2),
    fats_g DECIMAL(6, 2),
    quantity_g DECIMAL(6, 2),
    logged_at TIMESTAMP DEFAULT now(),
    created_at TIMESTAMP DEFAULT now()
);