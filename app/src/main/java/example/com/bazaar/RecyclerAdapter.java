package example.com.bazaar;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import example.com.bazaar.bean.ClubInfo;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private AllClubActivity allClub;
    private ArrayList<ClubInfo> clubList;
    private Firebase mRef;
    private String club_name;
    private String club_category;
    private String club_desc;
    private String club_admin;
    ClubInfo clubInfo;

    public RecyclerAdapter() {
        allClub = new AllClubActivity();
        clubList = new ArrayList<>();
        clubInfo = new ClubInfo();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView club_name;
        public TextView club_category;
        public TextView club_detail;
        public TextView club_admin;

        public ViewHolder(View itemView) {
            super(itemView);
            club_name = (TextView) itemView.findViewById(R.id.club_name);
            club_category = (TextView) itemView.findViewById(R.id.club_category);
            club_detail = (TextView) itemView.findViewById(R.id.club_detail);
            club_admin = (TextView) itemView.findViewById(R.id.club_admin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        mRef = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/Clubs");
        mRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                clubInfo = dataSnapshot.getValue(ClubInfo.class);
                setClub_name(clubInfo.getClubName());
                setClub_category(clubInfo.getClubCategory());
                setClub_desc(clubInfo.getClubDescription());
                setClub_admin(clubInfo.getClubAdmin());
                System.out.println("club anme is: " + clubInfo.getClubName());
                clubList.add(clubInfo);
                System.out.println("club second is: " + clubList.get(0).getClubName());
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.club_category.setText(clubList.get(i).getClubCategory());
        viewHolder.club_detail.setText(clubList.get(i).getClubDescription());
        viewHolder.club_name.setText(clubList.get(i).getClubName());
        viewHolder.club_admin.setText(clubList.get(i).getClubAdmin());
    }

    @Override
    public int getItemCount() {
        return clubList.size();
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getClub_category() {
        return club_category;
    }

    public void setClub_category(String club_category) {
        this.club_category = club_category;
    }

    public String getClub_desc() {
        return club_desc;
    }

    public void setClub_desc(String club_desc) {
        this.club_desc = club_desc;
    }

    public String getClub_admin() {
        return club_admin;
    }

    public void setClub_admin(String club_admin) {
        this.club_admin = club_admin;
    }
}