ALTER TABLE users ADD CONSTRAINT users_fk1   FOREIGN KEY (degree_id) REFERENCES r_degrees(id);
ALTER TABLE users ADD CONSTRAINT users_fk2   FOREIGN KEY (position_id) REFERENCES r_positions(id);
ALTER TABLE users ADD CONSTRAINT users_fk3   FOREIGN KEY (gender_id) REFERENCES r_gender(id);
