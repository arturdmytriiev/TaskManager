CREATE TABLE tasks(
    id DECIMAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL ,
    description TEXT,
    status VARCHAR(20) NOT NULL ,
    priority VARCHAR(10) NOT NULL ,
    due_date DATE,
    user_id INT NOT NULL ,
    FOREIGN KEY (user_id) references users(id) ON DELETE CASCADE
)