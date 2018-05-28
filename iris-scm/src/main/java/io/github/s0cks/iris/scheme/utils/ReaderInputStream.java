package io.github.s0cks.iris.scheme.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public final class ReaderInputStream
extends InputStream{
    private final Reader reader;

    public ReaderInputStream(Reader reader) {
        if(reader == null) throw new NullPointerException("reader == null");
        this.reader = reader;
    }

    @Override
    public int read() throws IOException {
        return this.reader.read();
    }

    @Override
    public void close() throws IOException{
        this.reader.close();
    }
}