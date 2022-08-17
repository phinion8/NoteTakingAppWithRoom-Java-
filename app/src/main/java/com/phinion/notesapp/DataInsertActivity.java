package com.phinion.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.phinion.notesapp.databinding.ActivityDataInsertBinding;

public class DataInsertActivity extends AppCompatActivity {

    ActivityDataInsertBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type = getIntent().getStringExtra("type");

        if (type.equals("update")) {

            binding.noteTitle.setText(getIntent().getStringExtra("title"));
            binding.noteDescription.setText(getIntent().getStringExtra("noteDescription"));
            int id = getIntent().getIntExtra("id", 0);
            binding.add.setText("UPDATE");
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.noteTitle.getText().toString());
                    intent.putExtra("noteDescription", binding.noteDescription.getText().toString());
                    intent.putExtra("id", id);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            });

        }else if (type.equals("addMode")){

            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.noteTitle.getText().toString());
                    intent.putExtra("noteDescription", binding.noteDescription.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DataInsertActivity.this, MainActivity.class));
        finish();
    }
}