CREATE TABLE auto_post (
        id INT PRIMARY KEY,
        description VARCHAR(255) NOT NULL,
        created TIMESTAMP NOT NULL,
        auto_user_id INT NOT NULL,
        FOREIGN KEY (auto_user_id) REFERENCES auto_user(id)
        );
