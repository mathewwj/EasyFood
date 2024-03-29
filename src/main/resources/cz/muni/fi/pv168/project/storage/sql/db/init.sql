--
-- Category table definition
--
CREATE TABLE IF NOT EXISTS "Category"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `name`    VARCHAR(150) NOT NULL,
    `color`    INTEGER
);


--
-- Recipe table definition
--
CREATE TABLE IF NOT EXISTS "Recipe"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `title`    VARCHAR(150) NOT NULL,
    `description`      VARCHAR(150) NOT NULL,
    `portionCount`      INTEGER,
    `instructions`      VARCHAR(150) NOT NULL,
    `timeToPrepare`      INTEGER,
    `category`      VARCHAR REFERENCES "Category"(`guid`)
);

--
-- Unit table definition
--
CREATE TABLE IF NOT EXISTS "Unit"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `name`    VARCHAR(150) NOT NULL,
    `abbreviation`    VARCHAR(150) NOT NULL,
    `ingredientType`    VARCHAR(150) NOT NULL,
    `conversionRate`    REAL
);

--
-- Ingredient table definition
--
CREATE TABLE IF NOT EXISTS "Ingredient"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `name`    VARCHAR(150) NOT NULL,
    `defaultUnit`    VARCHAR REFERENCES "Unit"(`guid`),
    `caloriesPerUnit`    REAL
);


--
-- RecipeIngredient table definition
--
CREATE TABLE IF NOT EXISTS "RecipeIngredient"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `recipe`    VARCHAR REFERENCES "Recipe"(`guid`),
    `ingredient`    VARCHAR REFERENCES "Ingredient"(`guid`),
    `unit`    VARCHAR REFERENCES "Unit"(`guid`),
    `amount`    INTEGER
);
