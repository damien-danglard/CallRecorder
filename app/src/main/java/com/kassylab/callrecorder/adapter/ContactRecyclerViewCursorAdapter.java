/*
 * Copyright (C) 2017  KassyLab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kassylab.callrecorder.adapter;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kassylab.callrecorder.R;
import com.kassylab.callrecorder.fragment.ContactsFragment;
import com.kassylab.recyclerviewcursoradapter.RecyclerViewCursorAdapter;

/**
 * {@link RecyclerView.Adapter} that can display an Item and makes a call to the
 * specified {@link ContactsFragment.OnContactSelectedListener}.
 */
public class ContactRecyclerViewCursorAdapter
		extends RecyclerViewCursorAdapter<ContactRecyclerViewCursorAdapter.ViewHolder> {
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.list_item_contact, parent, false);
		return new ViewHolder(view);
	}
	
	class ViewHolder extends RecyclerViewCursorAdapter.ViewHolder {
		
		//final TextView mIdView;
		final TextView mContentView;
		
		ViewHolder(View view) {
			super(view);
			//mIdView = view.findViewById(R.id.id);
			mContentView = view.findViewById(R.id.content);
		}
		
		@Override
		public String toString() {
			return super.toString() + " '" + mContentView.getText() + "'";
		}
		
		protected void bind(Cursor cursor) {
			int mIdColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
	        /*if (mIdColumn != -1) {
                //mIdView.setText(cursor.getLong(mIdColumn) + "");
            }*/
			int mLookupKeyColumn = cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
			int mDisplayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
			if (mDisplayNameColumn != -1) {
				mContentView.setText(cursor.getString(mDisplayNameColumn));
			}
			itemUri = ContactsContract.Contacts.getLookupUri(
					cursor.getLong(mIdColumn),
					cursor.getString(mLookupKeyColumn)
			);
		}
		
		@Override
		protected void onTouch(Uri itemUri, int position) {
			OnItemInteractionListener listener = getOnItemInteractionListener();
			if (listener != null) {
				listener.onItemSelected(itemUri, position);
			}
		}
	}
}
