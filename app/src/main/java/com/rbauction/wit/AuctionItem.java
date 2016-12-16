package com.rbauction.wit;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brucexia on 2016-05-13.
 */
public class AuctionItem extends AbstractItem<AuctionItem, AuctionItem.ViewHolder> {

    private static final ViewHolderFactory<? extends ViewHolder> FACTORY = new ItemFactory();

    public String header;
    public Auction auction;
    boolean flagged;
    public ContentDescriptionBuilder descriptionBuilder;

    public AuctionItem withHeader(String header) {
        this.header = header;
        return this;
    }

    @Override
    public long getIdentifier() {
        return Long.parseLong(auction.eventId);
    }

    public AuctionItem withAuction(Auction auction) {
        this.auction = auction;
        return this;
    }

    public AuctionItem withFlagged(boolean flagged) {
        this.flagged = flagged;
        return this;
    }

    public AuctionItem withContentDescriptionBuilder(ContentDescriptionBuilder builder) {
        descriptionBuilder = builder;
        return this;
    }


    @Override
    public int getType() {
        return R.id.auction_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_auction_list;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> lists) {
        holder.update(auction, flagged);
        super.bindView(holder, lists);

        String contentDescriptionBase = descriptionBuilder.build(header, holder.getAdapterPosition());
        holder.itemView.setContentDescription(contentDescriptionBase);
        holder.titleView.setContentDescription(contentDescriptionBase + ".title");
        holder.subtitleView.setContentDescription(contentDescriptionBase + ".subtitle");
        holder.dateView.setContentDescription(contentDescriptionBase + ".date");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView titleView;
        @BindView(R.id.text_subtitle)
        TextView subtitleView;
        @BindView(R.id.text_date)
        TextView dateView;
        @BindView(R.id.icon)
        AppCompatImageView exceptionIcon;

        Auction auction;
        //TODO: make this a dependency

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(final Auction auction, boolean flagged) {
            titleView.setText(auction.name);
            subtitleView.setText(auction.description);
            exceptionIcon.setImageResource(R.drawable.ic_check_circle_black_24dp);
            exceptionIcon.setEnabled(auction.isRegistrable);
            exceptionIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    auction.isRegistrable = false;
                    exceptionIcon.setEnabled(false);
                }
            });
            this.auction = auction;

        }

    }

    protected static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }

    @Override
    public ViewHolderFactory<? extends ViewHolder> getFactory() {
        return FACTORY;
    }

    public interface ContentDescriptionBuilder {
        String build(String header, int position);
    }


    public static class HeaderContentDescriptionBuilder implements ContentDescriptionBuilder {
        Context context;

        public HeaderContentDescriptionBuilder(Context context) {
            this.context = context;
        }

        @Override
        public String build(String header, int position) {
            StringBuilder builder = new StringBuilder();
            builder.append("auctions.grid.");
//			if (header.equals(context.getString(R.string.auctions_list_today))) {
//				builder.append("0");
//				builder.append("_").append(String.valueOf(position));
//			} else if (header.equals(context.getString(R.string.auctions_list_upcoming))) {
//				builder.append("1");
//				AccessibilityUtils.map(position);
//				builder.append("_").append(AccessibilityUtils.getAtPosition(position));
//			}

            return builder.toString();
        }
    }

    public static class DefaultContentDescriptionBuilder implements ContentDescriptionBuilder {
        Context context;

        public DefaultContentDescriptionBuilder(Context context) {
            this.context = context;
        }

        @Override
        public String build(String header, int position) {
            StringBuilder builder = new StringBuilder();
            builder.append("auctions.grid.").append(position);

            return builder.toString();
        }
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + auction.hashCode();
        return result;
    }
}
