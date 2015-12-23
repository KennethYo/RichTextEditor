package cn.kenneth.richedittextdemo;

import android.content.Context;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;

import com.rockerhieu.emojicon.EmojiconHandler;

import io.github.mthli.knife.KnifeText;

/**
 * 富文本编辑
 * Created by kenneth on 15/12/23.
 */
public class RichEditText extends KnifeText {
    private int mEmojiconSize;
    private int mEmojiconTextSize;

    public RichEditText(Context context) {
        super(context);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mEmojiconSize = (int) getTextSize();
        mEmojiconTextSize = (int) getTextSize();
        setText(getText());
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        updateText();
    }

    private void updateText() {
        EmojiconHandler.addEmojis(getContext(), getText(), mEmojiconSize, DynamicDrawableSpan.ALIGN_BASELINE,
                mEmojiconTextSize, false);
    }
}
