package com.example.kuotes.view.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuotes.R;
import com.example.kuotes.entity.AppDatabase;
import com.example.kuotes.model.quotes.QuotesResponse;
import com.example.kuotes.view.MainContact;
import com.example.kuotes.view.MainPresenter;
import com.example.kuotes.view.viewmodel.QuotesViewModel;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements MainContact.homeView,View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppDatabase appDatabase;
    private MainPresenter mainPresenter;

    private QuotesViewModel quotesViewModel;

    private TextView tvQuotes, tvby, tvAuthor;
    private ImageView ivCopy, ivFavo;
    private ImageButton btnGenerate;

    private boolean favorited = false;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvQuotes = view.findViewById(R.id.home_quotes);
        tvby = view.findViewById(R.id.home_by);
        tvAuthor = view.findViewById(R.id.home_author);
        ivCopy = view.findViewById(R.id.home_copy);
        ivFavo = view.findViewById(R.id.home_favorite);
        btnGenerate = view.findViewById(R.id.home_btnGenerate);

        ivCopy.setOnClickListener(this);
        ivFavo.setOnClickListener(this);
        btnGenerate.setOnClickListener(this);

        appDatabase = AppDatabase.iniDB(getContext());
        mainPresenter = new MainPresenter(this);

        quotesViewModel = new ViewModelProvider(this).get(QuotesViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_copy:
                ClipboardManager clipboard = getSystemService(getContext(),ClipboardManager.class);
                ClipData clip = ClipData.newPlainText("text", tvQuotes.getText().toString()+" - "+tvAuthor.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getContext(),"Copy to Clipboard",Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_favorite:
                if (!favorited){
                    // save ke database
                    mainPresenter.insertData(tvQuotes.getText().toString(),tvAuthor.getText().toString(),appDatabase);

                    ivFavo.setImageResource(R.drawable.ic_favorite);
                    favorited = true;
                }
                else{
                    // hapus dari database (hanya lewat menu favorite)
                    Toast.makeText(getContext(),"Delete quotes only from Favorite menu ",Toast.LENGTH_SHORT).show();
                    favorited = false;
                }
                break;
            case R.id.home_btnGenerate:
                quotesViewModel.setQuotes();
                quotesViewModel.getQuotes().observe(this,getQuotes);
                break;
        }
    }

    private Observer<QuotesResponse> getQuotes = new Observer<QuotesResponse>() {
        @Override
        public void onChanged(QuotesResponse quotesResponse) {
            if (quotesResponse != null){
                showQuotes(quotesResponse);
            }
        }
    };

    private void showQuotes(QuotesResponse quotesResponse) {
        tvby.setVisibility(View.VISIBLE);
        tvAuthor.setVisibility(View.VISIBLE);
        ivCopy.setVisibility(View.VISIBLE);
        ivFavo.setVisibility(View.VISIBLE);
        tvQuotes.setText(quotesResponse.getContent());
        tvAuthor.setText(quotesResponse.getAuthor());
    }

    @Override
    public void successAdd() {
        Toast.makeText(getContext(),"Berhasil Menambahkan Quotes",Toast.LENGTH_SHORT).show();
    }
}