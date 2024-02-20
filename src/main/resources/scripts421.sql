ALTER TABLE student add constraint age CHECK (age>15);

ALTER  TABLE student ALTER COLUMN name SET NOT NULL;
ALTER  TABLE student add constraint name_unique UNIQUE(name);

ALTER  TABLE faculty add constraint name_color UNIQUE(color);

ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;