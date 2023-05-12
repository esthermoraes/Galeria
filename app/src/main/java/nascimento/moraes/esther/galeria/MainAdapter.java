package nascimento.moraes.esther.galeria;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<String> photos;

    public MainAdapter(MainActivity mainActivity, List<String> photos) {
        this.mainActivity = mainActivity;
        this.photos = photos;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ImageView imPhoto = holder.itemView.findViewById(R.id.imItem);
        int w = (int)
                mainActivity.getResources().getDimension(R.dimen.itemWidth);
        int h = (int)
                mainActivity.getResources().getDimension(R.dimen.itemHeight);
        Bitmap bitmap = Utils.getBitmap(photos.get(position), w, h);
        imPhoto.setImageBitmap(bitmap);
        imPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startPhotoActivity(photos.get(position));
            }
        });
    }

    public class MainActivity extends AppCompatActivity {
        List<String> photos = new ArrayList<>();
        MainAdapter mainAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                photos.add(files[i].getAbsolutePath());
            }
            mainAdapter = new MainAdapter(MainActivity.this, photos);
            RecyclerView rvGallery = findViewById(R.id.rvGallery);
            rvGallery.setAdapter(mainAdapter);
            float w = getResources().getDimension(R.dimen.itemWidth);
            int numberOfColumns = Utils.calculateNoOfColumns(MainActivity.this, w);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns);
            rvGallery.setLayoutManager(gridLayoutManager);
        }
    }
}