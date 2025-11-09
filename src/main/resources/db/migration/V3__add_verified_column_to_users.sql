-- Add verified column to users table
-- Default value is false for new records
ALTER TABLE users
ADD COLUMN verified BOOLEAN NOT NULL DEFAULT FALSE;

-- Set existing records to verified = true
-- (assuming existing users should be considered verified)
UPDATE users SET verified = TRUE WHERE verified = FALSE;

-- Create index on verified column for better query performance
CREATE INDEX idx_users_verified ON users(verified);
