package com.example.scanbotexample;

import java.util.ArrayList;
import java.util.List;

import io.scanbot.sdk.persistence.Page;

public class PageRepository {
    private static final List<Page> pages = new ArrayList<>();
    public static void addPages(List<Page> page) {
        pages.addAll(page);
    }

    public static List<Page> getPages() {
        return pages;
    }
}
