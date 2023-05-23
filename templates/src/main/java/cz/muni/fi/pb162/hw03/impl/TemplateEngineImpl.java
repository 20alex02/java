package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.impl.nodes.For;
import cz.muni.fi.pb162.hw03.impl.nodes.If;
import cz.muni.fi.pb162.hw03.impl.nodes.Name;
import cz.muni.fi.pb162.hw03.impl.nodes.Node;
import cz.muni.fi.pb162.hw03.impl.nodes.Text;
import cz.muni.fi.pb162.hw03.impl.parser.tokens.Commands;
import cz.muni.fi.pb162.hw03.impl.parser.tokens.Token;
import cz.muni.fi.pb162.hw03.impl.parser.tokens.Tokenizer;
import cz.muni.fi.pb162.hw03.template.TemplateEngine;
import cz.muni.fi.pb162.hw03.template.TemplateException;
import cz.muni.fi.pb162.hw03.template.model.TemplateModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateEngineImpl implements TemplateEngine {
    private final Map<String, List<Node>> data = new HashMap<>();
    private int index = 0;
    private List<Token> tokens = null;

    public Map<String, List<Node>> getData() {
        return data;
    }

    private boolean checkName() {
        return tokens.get(index).getKind() == Token.Kind.OPEN
                && tokens.get(index + 1).getKind() == Token.Kind.NAME
                && tokens.get(index + 2).getKind() == Token.Kind.CLOSE;
    }

    private boolean checkIf() {
        return tokens.get(index).getKind() == Token.Kind.OPEN
                && tokens.get(index + 1).getKind() == Token.Kind.CMD
                && tokens.get(index + 1).cmd().equals(Commands.IF)
                && tokens.get(index + 2).getKind() == Token.Kind.NAME
                && tokens.get(index + 3).getKind() == Token.Kind.CLOSE;
    }

    private boolean checkFor() {
        return tokens.get(index).getKind() == Token.Kind.OPEN
                && tokens.get(index + 1).getKind() == Token.Kind.CMD
                && tokens.get(index + 1).cmd().equals(Commands.FOR)
                && tokens.get(index + 2).getKind() == Token.Kind.NAME
                && tokens.get(index + 3).getKind() == Token.Kind.IN
                && tokens.get(index + 4).getKind() == Token.Kind.NAME
                && tokens.get(index + 5).getKind() == Token.Kind.CLOSE;
    }

    private boolean checkDone() {
        return tokens.get(index).getKind() == Token.Kind.OPEN
                && tokens.get(index + 1).getKind() == Token.Kind.CMD
                && tokens.get(index + 1).cmd().equals(Commands.DONE)
                && tokens.get(index + 2).getKind() == Token.Kind.CLOSE;
    }

    private boolean checkElse() {
        return tokens.get(index).getKind() == Token.Kind.OPEN
                && tokens.get(index + 1).getKind() == Token.Kind.CMD
                && tokens.get(index + 1).cmd().equals(Commands.ELSE)
                && tokens.get(index + 2).getKind() == Token.Kind.CLOSE;
    }

    private boolean loadTemplateRecursive(List<Node> nodes) {
        while (index < tokens.size()) {
            try {
                if (tokens.get(index).getKind() == Token.Kind.TEXT) {
                    nodes.add(new Text(tokens.get(index).text()));
                    index++;
                } else if (checkName()) {
                    nodes.add(new Name(tokens.get(index + 1).name()));
                    index += 3;
                } else if (checkIf()) {
                    String name = tokens.get(index + 2).name();
                    index += 4;
                    List<Node> positiveBlock = new ArrayList<>();
                    List<Node> negativeBlock = null;
                    if (!loadTemplateRecursive(positiveBlock)) {
                        negativeBlock = new ArrayList<>();
                        if (!loadTemplateRecursive(negativeBlock)) {
                            throw new TemplateException("Missing DONE token");
                        }
                    }
                    nodes.add(new If(name, positiveBlock, negativeBlock));
                } else if (checkFor()) {
                    String variable = tokens.get(index + 2).name();
                    String iterable = tokens.get(index + 4).name();
                    index += 6;
                    List<Node> block = new ArrayList<>();
                    if (!loadTemplateRecursive(block)) {
                        throw new TemplateException("Missing DONE token");
                    }
                    nodes.add(new For(variable, iterable, block));
                } else if (checkElse()) {
                    index += 3;
                    return false;
                } else if (checkDone()) {
                    index += 3;
                    return true;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new TemplateException("Invalid template", e);
            }
        }
        return false;
    }

    @Override
    public void loadTemplate(String name, String text) {
        List<Node> nodes = new ArrayList<>();
        Tokenizer tokenizer = new Tokenizer(text);
        tokens = new ArrayList<>();
        index = 0;
        while (!tokenizer.done()) {
            tokens.add(tokenizer.consume());
        }
        loadTemplateRecursive(nodes);
        data.put(name, nodes);
    }

    @Override
    public Collection<String> getTemplateNames() {
        return data.keySet();
    }

    @Override
    public String evaluateTemplate(String name, TemplateModel model) {
        if (!data.containsKey(name)) {
            throw new TemplateException("Template with given name not found");
        }
        StringBuilder builder = new StringBuilder();
        data.get(name).forEach(node -> node.evaluate(model, builder));
        return builder.toString();
    }
}
