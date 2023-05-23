package cz.muni.fi.pb162.hw03.impl.nodes;

import cz.muni.fi.pb162.hw03.template.model.TemplateModel;

import java.util.List;

public class For extends Node {
    private final String variableName;
    private final String iterableName;
    private final List<Node> block;

    /**
     * Creates new for node
     * @param variableName variable name
     * @param iterableName iterable
     * @param block block
     */
    public For(String variableName, String iterableName, List<Node> block) {
        this.variableName = variableName;
        this.iterableName = iterableName;
        this.block = block;
    }
    @Override
    public void evaluate(TemplateModel model, StringBuilder builder) {
        for (Object o : model.getAsIterable(iterableName)) {
            block.forEach(node -> node.evaluate(model.extended(variableName, o), builder));
        }
    }
}

