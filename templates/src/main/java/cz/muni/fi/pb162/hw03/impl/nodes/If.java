package cz.muni.fi.pb162.hw03.impl.nodes;

import cz.muni.fi.pb162.hw03.template.model.TemplateModel;

import java.util.List;

public class If extends Node {
    private final String variableName;
    private final List<Node> positiveBlock;
    private final List<Node> negativeBlock;

    /**
     * Creates new if node
     * @param variableName variable name
     * @param positiveBlock true branch
     * @param negativeBlock false branch
     */
    public If(String variableName, List<Node> positiveBlock, List<Node> negativeBlock) {
        this.variableName = variableName;
        this.positiveBlock = positiveBlock;
        this.negativeBlock = negativeBlock;
    }
    @Override
    public void evaluate(TemplateModel model, StringBuilder builder) {
        if (!model.getAsBoolean(variableName) && negativeBlock != null) {
            negativeBlock.forEach(node -> node.evaluate(model, builder));
        } else {
            positiveBlock.forEach(node -> node.evaluate(model, builder));
        }
    }
}
