package com.tuyenmonkey.textdecorator;

import android.content.res.ColorStateList;
import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import com.tuyenmonkey.textdecorator.callback.OnTextClickListener;

/**
 * Created by Tuyen Nguyen on 12/24/16.
 */

public class TextDecorator {
  private TextView textView;
  private String content;
  private SpannableString decoratedContent;
  private int flag;

  private TextDecorator(TextView textView, String content) {
    this.textView = textView;
    this.content = content;
    this.decoratedContent = new SpannableString(content);
    this.flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
  }

  public static TextDecorator decorate(TextView textView, String content) {
    return new TextDecorator(textView, content);
  }

  public TextDecorator setFlag(final int flag) {
    this.flag = flag;

    return this;
  }

  public TextDecorator underline(final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new UnderlineSpan(), start, end, flag);

    return this;
  }

  public TextDecorator setTextColor(final @ColorRes int resColorId, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new ForegroundColorSpan(ContextCompat.getColor(textView.getContext(), resColorId)), start, end, flag);

    return this;
  }

  public TextDecorator setBackgroundColor(final @ColorRes int colorResId, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new BackgroundColorSpan(ContextCompat.getColor(textView.getContext(), colorResId)), start, end, 0);

    return this;
  }

  public TextDecorator insertBullet(final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new BulletSpan(), start, end, flag);

    return this;
  }

  public TextDecorator insertBullet(final int gapWidth, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new BulletSpan(gapWidth), start, end, flag);

    return this;
  }

  public TextDecorator insertBullet(final int gapWidth, @ColorRes int colorResId, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new BulletSpan(gapWidth, ContextCompat.getColor(textView.getContext(), colorResId)), start, end, flag);

    return this;
  }

  public TextDecorator makeTextClickable(final OnTextClickListener listener, final int start, final int end, final boolean underlineText) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new ClickableSpan() {
      @Override public void onClick(View view) {
        listener.onClick(view, content.substring(start, end));
      }

      @Override public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(underlineText);
      }
    }, start, end, flag);
    textView.setMovementMethod(LinkMovementMethod.getInstance());

    return this;
  }

  public TextDecorator makeTextClickable(final ClickableSpan clickableSpan, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(clickableSpan, start, end, flag);
    textView.setMovementMethod(LinkMovementMethod.getInstance());

    return this;
  }

  public TextDecorator insertImage(final @DrawableRes int resId, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new ImageSpan(textView.getContext(), resId), start, end, flag);

    return this;
  }

  public TextDecorator quote(final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new QuoteSpan(), start, end, flag);

    return this;
  }

  public TextDecorator quote(final @ColorRes int colorResId, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new QuoteSpan(ContextCompat.getColor(textView.getContext(), colorResId)), start, end, flag);

    return this;
  }

  public TextDecorator strikethrough(final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new StrikethroughSpan(), start, end, flag);

    return this;
  }

  public TextDecorator setTextStyle(final int style, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new StyleSpan(style), start, end, flag);

    return this;
  }

  public TextDecorator alignText(final Layout.Alignment alignment, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new AlignmentSpan.Standard(alignment), start, end, flag);

    return this;
  }

  public TextDecorator setSubscript(final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new SubscriptSpan(), start, end, flag);

    return this;
  }

  public TextDecorator setSuperscript(final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new SuperscriptSpan(), start, end, flag);

    return this;
  }

  public TextDecorator setTypeface(final String family, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new TypefaceSpan(family), start, end, flag);

    return this;
  }

  public TextDecorator setTextAppearance(final int appearance, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new TextAppearanceSpan(textView.getContext(), appearance), start, end, flag);

    return this;
  }

  public TextDecorator setTextAppearance(final int appearance, final int colorList, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new TextAppearanceSpan(textView.getContext(), appearance, colorList), start, end, flag);

    return this;
  }

  public TextDecorator setTextAppearance(String family, int style, int size, ColorStateList color, ColorStateList linkColor, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new TextAppearanceSpan(family, style, size, color, linkColor), start, end, flag);

    return this;
  }

  public TextDecorator setAbsoluteSize(final int size, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new AbsoluteSizeSpan(size), start, end, flag);

    return this;
  }

  public TextDecorator setAbsoluteSize(final int size, final boolean dip, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new AbsoluteSizeSpan(size, dip), start, end, flag);

    return this;
  }

  public TextDecorator setRelativeSize(final float proportion, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new RelativeSizeSpan(proportion), start, end, flag);

    return this;
  }

  public TextDecorator scaleX(final float proportion, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new ScaleXSpan(proportion), start, end, flag);

    return this;
  }

  public TextDecorator blur(final float radius, final BlurMaskFilter.Blur style, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new BlurMaskFilter(radius, style), start, end, flag);

    return this;
  }

  public TextDecorator emboss(final float[] direction, final float ambient, final float specular, final float blurRadius, final int start, final int end) {
    checkIndexOutOfBoundsException(start, end);
    decoratedContent.setSpan(new EmbossMaskFilter(direction, ambient, specular, blurRadius), start, end, flag);

    return this;
  }

  public void build() {
    textView.setText(decoratedContent);
  }

  private void checkIndexOutOfBoundsException(final int start, final int end) {
    if (start < 0) {
      throw new IndexOutOfBoundsException("start is less than 0");
    } else if (end > content.length()) {
      throw new IndexOutOfBoundsException("end is greater than content length " + content.length());
    } else if (start > end) {
      throw new IndexOutOfBoundsException("start is greater than end");
    }
  }
}
