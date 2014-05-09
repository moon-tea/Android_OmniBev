package edu.tableservice;

import java.util.List;

import edu.tableservice.data.Check;
import edu.udallas.tableservice.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TableListAdapter extends ArrayAdapter<Check> {

	public TableListAdapter(Context context, List<Check> checkList) {
		super(context, 0, checkList);
	}
	
	/**
	 * This method is called by any ListView that is using this
	 * adapter whenever it needs a view for a list item
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Holder holder;
		
		if (view == null) {
			// Create a new view, inflating it from an XML resource.
			// Don't attach to parent, because the ListView will do that for us
			view = LayoutInflater.from(getContext()).inflate(R.layout.item_table, parent, false);
			
			// This is called the Holder pattern.  
			// It makes it easier and faster to re-find views in a list item later
			holder = new Holder();
			holder.txtTableName = (TextView) view.findViewById(R.id.table_name);
			holder.txtTableList = (TextView) view.findViewById(R.id.table_list);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		
		// Get the check from the data list the Adapter is holding on to
		Check check = getItem(position);
		
		// Set the content of the list item view (in this case, its just the table name)
		holder.txtTableName.setText(check.getTableName());
		//Toast.makeText(getContext(), check.getList(), Toast.LENGTH_SHORT).show();
		holder.txtTableList.setText(check.getList());
		
		// Return the view to the ListView that asked for it
		return view;
	}	
	
	private static class Holder {
		TextView txtTableName;
		TextView txtTableList;
	}
	
}
