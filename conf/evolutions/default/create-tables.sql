# --- !Ups

CREATE TABLE word (
  id UUID NOT NULL PRIMARY KEY,
  word VARCHAR(255) NOT NULL,
  pronunciation VARCHAR(300) NOT NULL,
  type VARCHAR(50) NOT NULL,
  sound VARCHAR(50) NOT NULL
);

CREATE TABLE definition(
  id UUID NOT NULL PRİMARY KEY,
  definition VARCHAR(255),
  id_word UUID,

  CONSTRAINT fk_definition_word FOREIGN KEY(id_word) REFERENCES word(id)
);

CREATE TABLE sentence(
  id UUID NOT NULL PRIMARY KEY,
  sentence VARCHAR(255) NOT NULL,
  id_definition UUID NOT NULL,

  CONSTRAINT fk_sentence_definition FOREIGN KEY (id_definition) REFERENCES definition(id)
);

# --- !Downs

