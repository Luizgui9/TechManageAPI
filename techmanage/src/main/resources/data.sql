MERGE INTO users AS target
USING (VALUES
    (1, 'Alice Johnson', 'alice@example.com', '+55 12 34977-2108', '1990-05-15', 'ADMIN'),
    (2, 'Bob Smith', 'bob@example.com', '+55 11 36791-5784', '1985-10-22', 'VIEWER'),
    (3, 'Ruan', 'ru_an@example.com', '+55 19 34329-2754', '2000-11-05', 'EDITOR')
) AS source (user_id, full_name, email, phone, birth_date, user_type)
ON target.email = source.email
WHEN NOT MATCHED THEN
    INSERT (user_id, full_name, email, phone, birth_date, user_type)
    VALUES (source.user_id, source.full_name, source.email, source.phone, source.birth_date, source.user_type);