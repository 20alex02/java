package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.template.FSTemplateEngine;
import cz.muni.fi.pb162.hw03.template.model.TemplateModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FSTemplateEngineImpl extends TemplateEngineImpl implements FSTemplateEngine {

    @Override
    public void loadTemplate(Path file, Charset cs, String ext) {
        String name = String.valueOf(file.getFileName());
        if (name.endsWith(ext)) {
            name = name.substring(0, name.length() - ext.length() - 1);
        }
        String text;
        try {
            text = Files.readString(file, cs);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
        loadTemplate(name, text);
    }

    @Override
    public void loadTemplateDir(Path inDir, Charset cs, String ext) {
        File[] files = inDir.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            loadTemplate(Path.of(file.getPath()), cs, ext);
        }
    }

    @Override
    public void writeTemplate(String name, TemplateModel model, Path file, Charset cs) {
        String evaluatedTemplate = evaluateTemplate(name, model);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file.toFile()), cs)) {
            writer.write(evaluatedTemplate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeTemplates(TemplateModel model, Path outDir, Charset cs) {
        for (String name : getData().keySet()) {
            writeTemplate(name, model, Paths.get(String.valueOf(outDir), name), cs);
        }
    }
}
