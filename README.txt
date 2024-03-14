Allgemeine Informatik 1 Sommersemester 2021
Programmierprojekt : Pacman

Wir haben als Tutoren ein Spiel entwickelt, in dem die Studenten einzelne Funktionen ergänzen mussten. Im folgenden sehen Sie die Klassen und Funktionen, die ich programmiert habe:


src:
    -->model:
            entity -> KLassen: Ghost, GameEntity, Pacman
            util -> Klassen: consists

    -->level:
            PacmanGame -> KLassen: saveGame, changeLevel, getEntities, getPacman, movePacman, checkCollisions
            HighscoreEntry -> Klassen: attributes, HighscoreEntry, equals, compareTo