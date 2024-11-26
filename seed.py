import psycopg2

# Connection parameters for PostgreSQL (ensure these match your Docker Compose setup)
conn = psycopg2.connect(
    host="db",  # Service name of the database container in docker-compose.yml
    database="taskmanager",
    user="postgres",
    password="postgres"
)

cur = conn.cursor()

# Create and seed the task table
cur.execute("""
    CREATE TABLE IF NOT EXISTS task (
        id SERIAL PRIMARY KEY,
        title VARCHAR(255),
        description TEXT,
        completed BOOLEAN
    );
""")

# Seed the table only if it's empty
cur.execute("SELECT COUNT(*) FROM task;")
row_count = cur.fetchone()[0]
if row_count == 0:
    cur.execute("""
        INSERT INTO task (title, description, completed)
        VALUES
        ('Task 1', 'Description of Task 1', false),
        ('Task 2', 'Description of Task 2', true);
    """)
    print("Database was empty. Tasks have been added.")
else:
    print(f"Database already contains {row_count} tasks. No seeding required.")


conn.commit()
cur.close()
conn.close()

print("Database seeded successfully!")

