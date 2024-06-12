package com.example.scanbotexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.scanbot.sdk.ScanbotSDK;
import io.scanbot.sdk.persistence.Page;
import io.scanbot.sdk.persistence.PageFileStorage;

public class PagePreviewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PagesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_preview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adapter = new PagesAdapter(new ScanbotSDK(this).createPageFileStorage());
        adapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.pages_preview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setItems(PageRepository.getPages());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private class PagesAdapter extends RecyclerView.Adapter<PageViewHolder> {
        private PageFileStorage pageFileStorage;
        private List<Page> items = new ArrayList<>();
        //private View.OnClickListener mOnClickListener = new PageClickListener();

        PagesAdapter(PageFileStorage pageFileStorage) {
            this.pageFileStorage = pageFileStorage;
        }

        public void setItems(List<Page> pages) {
            items.clear();
            items.addAll(pages);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false);
            //view.setOnClickListener(mOnClickListener);
            return new PageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PageViewHolder holder, int position) {
            Page page = items.get(position);

            String imagePath = pageFileStorage.getPreviewImageURI(page.getPageId(), PageFileStorage.PageFileType.DOCUMENT).getPath();
            String originalImagePath = pageFileStorage.getPreviewImageURI(page.getPageId(), PageFileStorage.PageFileType.ORIGINAL).getPath();
            File fileToShow = new File(imagePath).exists() ? new File(imagePath) : new File(originalImagePath);
            PicassoHelper.with(getApplicationContext())
                    .load(fileToShow)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .resize(600, 600)
                    .centerInside()
                    .into(holder.imageView);
        }

        @Override
        public long getItemId(int position) {
            return items.get(position).hashCode();
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class PageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        PageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.page);
        }
    }
}