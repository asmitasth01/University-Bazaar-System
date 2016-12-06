package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import example.com.bazaar.bean.EventInfo;

public class EventPage extends Home {
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_event_page, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);
        mDatabase = FirebaseDatabase.getInstance().getReference("Bazaar").child("Events");
        mBlogList = (RecyclerView) findViewById(R.id.post);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<EventInfo, BlogViewHolder> fireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<EventInfo, BlogViewHolder>(
                EventInfo.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, EventInfo model, int position) {
                viewHolder.setTitle(model.getEventName());
                viewHolder.setDesc(model.getEventDescription());
                viewHolder.setImage(getApplicationContext(), model.getEventImageUrl());
                viewHolder.setDateAndTime("Date: " + model.getEventDate() + ", @ " + model.getEventTime());
                viewHolder.setLocation("Location: " + model.getEventLocation());
            }
        };
        mBlogList.setAdapter(fireBaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDateAndTime(String dateAndTime) {
            TextView post_date_time = (TextView) mView.findViewById(R.id.post_date_time);
            post_date_time.setText(dateAndTime);
        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setLocation(String location) {
            TextView post_desc = (TextView) mView.findViewById(R.id.post_location);
            post_desc.setText(location);
        }

        public void setImage(Context ctx, String image) {

            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        addItem = menu.findItem(R.id.addButton);
        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        addItem.setVisible(true);
        settingsItem.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.addButton) {
            startActivity(new Intent(this, CreateEventActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
