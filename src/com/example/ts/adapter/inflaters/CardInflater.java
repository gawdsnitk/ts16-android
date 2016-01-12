package com.example.ts.adapter.inflaters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ts.R;
import com.example.ts.adapter.BaseInflaterAdapter;
import com.example.ts.adapter.CardItemData;
import com.example.ts.adapter.IAdapterViewInflater;

public class CardInflater implements IAdapterViewInflater<CardItemData>
{
	@Override
	public View inflate(final BaseInflaterAdapter<CardItemData> adapter, final int pos, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.list_item_card, parent, false);
			holder = new ViewHolder(convertView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		final CardItemData item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder
	{
		private View m_rootView;
		private TextView m_text1;
		

		public ViewHolder(View rootView)
		{
			m_rootView = rootView;
			m_text1 = (TextView) m_rootView.findViewById(R.id.text1);
			
			rootView.setTag(this);
		}

		public void updateDisplay(CardItemData item)
		{
			m_text1.setText(item.getText1());
			
		}
	}
}
