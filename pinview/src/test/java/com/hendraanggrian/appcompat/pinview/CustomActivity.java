package com.hendraanggrian.appcompat.pinview;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hendraanggrian.appcompat.pinview.test.R;

public class CustomActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.AppTheme);
  }
}
