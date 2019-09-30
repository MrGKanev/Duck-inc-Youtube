package com.devteam.youtubemusic.ui.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.devteam.youtubemusic.R;

import androidx.recyclerview.widget.RecyclerView;

public class DividerDecoration extends RecyclerView.ItemDecoration
{
    private static final int[] ATTRS = {android.R.attr.listDivider};

    private Drawable divider;
    private int insets;

    public DividerDecoration(Context context)
    {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        divider = a.getDrawable(0);
        a.recycle();

        insets = context.getResources().getDimensionPixelSize(R.dimen.card_insets);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        drawVertical(c, parent);
    }

    /**
     * Draw dividers underneath each child view
     */
    public void drawVertical(Canvas c, RecyclerView parent)
    {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin + insets;
            final int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        // We can supply forced insets for each item view here in the Rect
        outRect.set(insets, insets, insets, insets);
    }
}
