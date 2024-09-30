CREATE TABLE IF NOT EXISTS parking_spots (
                                            id SERIAL PRIMARY KEY,
                                            name VARCHAR(255) NOT NULL,
    reserved BOOLEAN DEFAULT FALSE
    );
