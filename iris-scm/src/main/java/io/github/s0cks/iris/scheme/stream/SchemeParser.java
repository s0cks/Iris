package io.github.s0cks.iris.scheme.stream;

import io.github.s0cks.iris.scheme.type.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public final class SchemeParser
implements Closeable{
    private static final Set<Character> validSymbols = new HashSet<>();
    static{
        add('_');
        add('*');
        add('/');
        add('+');
        add('-');
        add('!');
        add('>');
        add('<');
        add('\'');
        add('?');
    }

    private static void add(char sym){
        validSymbols.add(sym);
    }

    private static boolean isValidSymbol(char sym){
        return validSymbols.contains(sym);
    }

    private final InputStream in;
    private char peek;
    private int row = 1;
    private int col = 1;

    public SchemeParser(InputStream in){
        if(in == null) throw new NullPointerException("Input == null");
        this.in = in;
        this.peek = '\0';
    }

    public SchemeObject parse() throws IOException{
        char next = this.peek();
        switch(next){
            case '(': return this.parseTail();
            default: return this.nextObject();
        }
    }

    private SchemeObject parseTail() throws IOException{
        char next = this.next();
        switch(next){
            case ')': return SchemeNil.NIL;
            default: return SchemeList.of(this.parse(), this.parseTail());
        }
    }

    private SchemeObject nextObject() throws IOException{
        char next = this.nextReal();
        switch(next){
            case '#':
                switch(next = this.next()){
                    case 't': case 'T': return SchemeBoolean.TRUE;
                    case 'f': case 'F': return SchemeBoolean.FALSE;
                    default: return this.nextObject();
                }
            case ';':
                while((next = this.next()) != '\n');
                return this.nextObject();
            case '(':
                return SchemeList.of(this.parse(), this.parseTail());
            default:
                if(Character.isAlphabetic(next) || isValidSymbol(next)){
                    StringBuilder buff = new StringBuilder();
                    buff.append(next);

                    while(Character.isAlphabetic(next = this.peek()) || isValidSymbol(next)){
                        buff.append(this.next());
                    }

                    return new SchemeSymbol(buff.toString());
                } else if(Character.isDigit(next)){
                    StringBuilder buff = new StringBuilder();
                    buff.append(next);

                    boolean period = false;
                    while(Character.isDigit(next = this.peek()) || (next == '.' && !period)){
                        buff.append(this.next());
                        if(next == '.' && !period) period = true;
                    }

                    return new SchemeNumber(Double.valueOf(buff.toString()));
                } else if(next == '"'){
                    StringBuilder buff = new StringBuilder();
                    while((next = this.peek()) != '"'){
                        switch(next){
                            case '\\':
                                this.next();
                                switch((next = this.next())){
                                    case 'n': buff.append("\n"); break;
                                    case 't': buff.append("\t"); break;
                                    case 'r': buff.append("\r"); break;
                                    default: buff.append("\\").append(next); break;
                                }
                            default:
                                buff.append(this.next());
                        }
                    }
                    this.next();
                    return new SchemeString(buff.toString());
                }
        }

        throw new IllegalStateException("Unexpected token: " + next);
    }

    private char nextReal() throws IOException{
        char next;
        while(Character.isWhitespace(next = this.next()));
        return next;
    }

    private char next() throws IOException{
        char next;
        if(this.peek != '\0'){
            next = this.peek;
            this.peek = '\0';
            this.col++;
            switch(next){
                case '\n':
                    this.col = 1;
                    this.row++;
                default:
                    return next;
            }
        }

        int read = this.in.read();
        if(read == -1){
            return '\0';
        } else{
            this.col++;
            switch((next = (char) read)){
                case '\n':
                    this.col = 1;
                    this.row++;
                default:
                    return next;
            }
        }
    }

    private char peek() throws IOException{
        if(this.peek != '\0') return this.peek;
        int peek = this.in.read();
        if(peek == -1){
            return '\0';
        } else{
            this.peek = (char) peek;
        }
        return this.peek;
    }

    @Override
    public void close() throws IOException{
        this.in.close();
    }
}