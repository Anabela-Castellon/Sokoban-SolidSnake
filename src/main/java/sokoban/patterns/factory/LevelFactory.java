package sokoban.patterns.factory;

import sokoban.model.Level;

import java.io.IOException;

public abstract class LevelFactory {

    public abstract Level createLevel(String resourcePath, int levelNumber) throws IOException;
}