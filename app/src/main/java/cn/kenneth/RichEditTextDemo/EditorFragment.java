package cn.kenneth.richedittextdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rockerhieu.emojicon.emoji.Emojicon;

import io.github.mthli.knife.KnifeTagHandler;
import io.github.mthli.knife.KnifeText;

/**
 * 文本编辑框
 * Created by kenneth on 15/12/22.
 */
public class EditorFragment extends Fragment {
    private static final String BOLD = "<p><b>这是粗体</b></p>";
    private static final String ITALIT = "<p><i>这是斜体</i></p>";
    private static final String UNDERLINE = "<p><u>这是下划线</u></p>";
    private static final String STRIKETHROUGH = "<p><s>这是删除线</s></p>"; // <s> or <strike> or <del>
    private static final String BULLET = "<p><ul><li>这是项目符号</li></ul></p>";
    private static final String QUOTE = "<p><blockquote>这是引用</blockquote></p>";
    private static final String LINK = "<p><a href=\"https://github.com/KennethYo/RichTextEditor\">超链接在这里</a></p>";
    private static final String EXAMPLE = BOLD + ITALIT + UNDERLINE + STRIKETHROUGH + BULLET + QUOTE + LINK;
    private KnifeText mKnifeText;

    public static Fragment newInstance() {
        return new EditorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.framgent_editor, container, false);
    }

    private View findViewById(@IdRes int id) {
        if (id < 0 || getView() == null) return new View(getActivity().getApplicationContext());
        return getView().findViewById(id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mKnifeText = (KnifeText) findViewById(R.id.knife);
        mKnifeText.setText(Html.fromHtml(EXAMPLE, null, new KnifeTagHandler()));
        mKnifeText.swicthToKnifeStyle();
        mKnifeText.setSelection(mKnifeText.getEditableText().length());

        setupBold();
        setupItalic();
        setupUnderline();
        setupStrikethrough();
        setupBullet();
        setupQuote();
        setupLink();
        setupClear();
        setupEmoji1();
        setupEmoji2();
        setupEmoji3();
    }

    public void input(EditText editText, String emojicon) {
        if (editText == null || emojicon == null) {
            return;
        }

        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start < 0) {
            editText.append(emojicon);
        } else {
            editText.getText().replace(Math.min(start, end), Math.max(start, end), emojicon, 0, emojicon.length());
        }
    }

    private void setupEmoji1() {
        ImageButton emoji1 = (ImageButton) findViewById(R.id.emoji_1);

        emoji1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(mKnifeText, Emojicon.newString(0x1f604));
            }
        });
    }

    private void setupEmoji2() {
        ImageButton emoji2 = (ImageButton) findViewById(R.id.emoji_2);

        emoji2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(mKnifeText, Emojicon.newString(0x1f60d));
            }
        });
    }

    private void setupEmoji3() {
        ImageButton emoji3 = (ImageButton) findViewById(R.id.emoji_3);

        emoji3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(mKnifeText, Emojicon.newString(0x1f61d));
            }
        });

    }

    private void setupBold() {
        ImageButton bold = (ImageButton) findViewById(R.id.bold);

        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKnifeText.bold(!mKnifeText.contains(KnifeText.FORMAT_BOLD));
            }
        });

    }

    private void setupItalic() {
        ImageButton italic = (ImageButton) findViewById(R.id.italic);

        italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKnifeText.italic(!mKnifeText.contains(KnifeText.FORMAT_ITALIC));
            }
        });

    }

    private void setupUnderline() {
        ImageButton underline = (ImageButton) findViewById(R.id.underline);

        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKnifeText.underline(!mKnifeText.contains(KnifeText.FORMAT_UNDERLINED));
            }
        });

    }

    private void setupStrikethrough() {
        ImageButton strikethrough = (ImageButton) findViewById(R.id.strikethrough);

        strikethrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKnifeText.strikethrough(!mKnifeText.contains(KnifeText.FORMAT_STRIKETHROUGH));
            }
        });

    }

    private void setupBullet() {
        ImageButton bullet = (ImageButton) findViewById(R.id.bullet);

        bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKnifeText.bullet(!mKnifeText.contains(KnifeText.FORMAT_BULLET));
            }
        });


    }

    private void setupQuote() {
        ImageButton quote = (ImageButton) findViewById(R.id.quote);

        quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKnifeText.quote(!mKnifeText.contains(KnifeText.FORMAT_QUOTE));
            }
        });

    }

    private void setupLink() {
        ImageButton link = (ImageButton) findViewById(R.id.link);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLinkDialog();
            }
        });

    }

    private void setupClear() {
        ImageButton clear = (ImageButton) findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKnifeText.clearFormats();
            }
        });

    }

    private void showLinkDialog() {
        final int start = mKnifeText.getSelectionStart();
        final int end = mKnifeText.getSelectionEnd();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);

        View view = View.inflate(getActivity(), R.layout.dialog_link, null);
        final EditText editText = (EditText) view.findViewById(R.id.edit);
        builder.setView(view);
        builder.setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String link = editText.getText().toString().trim();
                if (TextUtils.isEmpty(link)) {
                    return;
                }

                // When KnifeText lose focus, use this method
                mKnifeText.link(link, start, end);
            }
        });

        builder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // DO NOTHING HERE
            }
        });

        builder.create().show();
    }
}
