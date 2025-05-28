package com.example.todolist;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextTitle, editTextDescription;
    Button buttonAdd, buttonDelete;
    LinearLayout taskContainer;

    // Хранит выбранный таск для удаления
    View selectedTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDelete = findViewById(R.id.buttonDelete);
        taskContainer = findViewById(R.id.taskContainer);

        buttonAdd.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Введите заголовок задачи", Toast.LENGTH_SHORT).show();
                return;
            }

            addTask(title, description);
            editTextTitle.setText("");
            editTextDescription.setText("");
        });

        buttonDelete.setOnClickListener(v -> {
            if (selectedTask != null) {
                taskContainer.removeView(selectedTask);
                selectedTask = null;
                buttonDelete.setEnabled(false);
                Toast.makeText(this, "Задача удалена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTask(String title, String description) {
        LinearLayout taskLayout = new LinearLayout(this);
        taskLayout.setOrientation(LinearLayout.VERTICAL);
        taskLayout.setPadding(16, 16, 16, 16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 16);
        taskLayout.setLayoutParams(params);

        TextView titleView = new TextView(this);
        titleView.setText(title);
        titleView.setTextSize(18);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setPadding(0, 0, 0, 8);
        taskLayout.addView(titleView);

        TextView descriptionView = new TextView(this);
        descriptionView.setText(description.isEmpty() ? "(без описания)" : description);
        descriptionView.setVisibility(View.GONE);
        taskLayout.addView(descriptionView);

        titleView.setOnClickListener(v -> {
            // Показать/скрыть описание
            descriptionView.setVisibility(descriptionView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

            // Выделить текущий таск
            if (selectedTask != null && selectedTask != taskLayout) {
                // Снять выделение с предыдущего
                selectedTask.setBackgroundColor(0x00000000);
            }
            selectedTask = taskLayout;

            // Подсветка выбранной задачи (легкий серый фон)
            selectedTask.setBackgroundColor(0xFFE0E0E0);

            // Активировать кнопку удаления
            buttonDelete.setEnabled(true);
        });

        taskContainer.addView(taskLayout);
    }
}
