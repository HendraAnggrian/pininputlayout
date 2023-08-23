package com.hendraanggrian.appcompat.pinview.widget;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.TextView;
import androidx.core.widget.TextViewCompat;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

final class PinUtils {
  private static final ThreadLocal<Map<String, Constructor<PinEditText>>> sConstructors =
      new ThreadLocal<>();
  private static final Class<?>[] CONSTRUCTOR_PARAMS = new Class<?>[]{Context.class};

  private PinUtils() {
  }

  static void setTextAppearance(TextView view, int res) {
    if (Build.VERSION.SDK_INT >= 23) {
      view.setTextAppearance(res);
    } else {
      TextViewCompat.setTextAppearance(view, res);
    }
  }

  /**
   * Stolen from {@code CoordinatorLayout.parseBehavior(Context, AttributeSet, String)}.
   */
  static PinEditText parsePinEditText(Context context, String name) {
    if (TextUtils.isEmpty(name)) {
      return null;
    }
    final String fullName;
    if (name.startsWith(".")) {
      // Relative to the app package. Prepend the app package name.
      fullName = context.getPackageName() + name;
    } else if (name.indexOf('.') >= 0) {
      // Fully qualified package name.
      fullName = name;
    } else {
      // Assume stock behavior in this package.
      fullName = "com.hendraanggrian.appcompat.pinview.widget" + name;
    }
    try {
      Map<String, Constructor<PinEditText>> constructors = sConstructors.get();
      if (constructors == null) {
        constructors = new HashMap<>();
        sConstructors.set(constructors);
      }
      Constructor<PinEditText> c = constructors.get(fullName);
      if (c == null) {
        final Class<PinEditText> clazz = (Class<PinEditText>) Class
            .forName(fullName, true, context.getClassLoader());
        c = clazz.getConstructor(CONSTRUCTOR_PARAMS);
        constructors.put(fullName, c);
      }
      return c.newInstance(context);
    } catch (Exception e) {
      throw new RuntimeException("Could not inflate Behavior subclass " + fullName, e);
    }
  }
}
