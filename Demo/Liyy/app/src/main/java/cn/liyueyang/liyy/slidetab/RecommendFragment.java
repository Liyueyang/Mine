package cn.liyueyang.liyy.slidetab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.liyueyang.liyy.R;

public class RecommendFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View view=inflater.inflate(R.layout.follow_test,container,false);
		TextView  textView=(TextView) view.findViewById(R.id.content);
		textView.setText("推荐");
		return view;
	}
}
