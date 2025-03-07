package com.brick.codesnippetbackend.vo;

public enum LanguageChoose {
    PYTHON("Python"),
    JAVASCRIPT("JavaScript"),
    JAVA("Java"),
    C_PLUS_PLUS("C++"),
    C_SHARP("C#"),
    PHP("PHP"),
    RUBY("Ruby"),
    SWIFT("Swift"),
    GO("Go"),
    RUST("Rust"),
    TYPESCRIPT("TypeScript"),
    KOTLIN("Kotlin"),
    SQL("SQL"),
    PL_SQL("PL/SQL"),
    T_SQL("T-SQL"),
    HTML("HTML"),
    CSS("CSS"),
    SCSS("SCSS"),
    LESS("Less"),
    JSX("JSX"),
    TSX("TSX"),
    JSON("JSON"),
    XML("XML"),
    YAML("YAML"),
    TOML("TOML"),
    MARKDOWN("Markdown"),
    SHELL("Shell"),
    POWERSHELL("PowerShell"),
    BATCH("Batch"),
    DOCKERFILE("Dockerfile"),
    GITIGNORE("Gitignore"),
    VUE("Vue"),
    SVELTE("Svelte"),
    GRAPHQL("GraphQL"),
    R("R"),
    DART("Dart"),
    LUA("Lua"),
    PERL("Perl"),
    SCALA("Scala"),
    CLOJURE("Clojure"),
    GROOVY("Groovy"),
    VB_NET("VB.NET"),
    OBJECTIVE_C("Objective-C"),
    MATLAB("Matlab"),
    TEXT("Text"),
    OTHER("Other");

    private final String name;

    LanguageChoose(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}