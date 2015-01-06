package com.example.treenavsample;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import eu.reply.androidlab.ui.treenav.adapter.TreeAdapter;
import eu.reply.androidlab.ui.treenav.tree.NAryTree;
import eu.reply.androidlab.ui.treenav.tree.TreeNode;
import eu.reply.androidlab.ui.treenav.utils.CommandTreeNode;
import eu.reply.androidlab.ui.treenav.utils.CommandTreeNode.Command;

public class MainActivity extends Activity {

	private ListView mListView;
	
	TreeAdapter<String> listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListView = (ListView) findViewById(R.id.list_view);
		
		listAdapter = new TreeAdapter<String>(this, new MenuCellListFactory(), initialiseMenu(this));
		mListView.setAdapter(listAdapter);
		mListView.setOnItemClickListener(listAdapter);
	}
	
	
	/**
	 * Creates the initial representation of the data set that will be use for
	 * the MainMenuView instances.
	 * 
	 * <p>
	 * The initial collection contains the root of the tree (Home) and its
	 * children (menu items that make up the application's main menu).
	 * </p>
	 */
	private ArrayList<CommandTreeNode<String>> initialiseMenu(Context context) {

		NAryTree<String> menuDataSourceTree = getTreeMenu(context);

		ArrayList<CommandTreeNode<String>> listDataSet = new ArrayList<CommandTreeNode<String>>();
		listDataSet.add((CommandTreeNode<String>) menuDataSourceTree.getRoot());

		for (TreeNode<String> mainMenuItemNode : menuDataSourceTree.getRoot().getChildren()) {
			listDataSet.add((CommandTreeNode<String>) mainMenuItemNode);
		}
		
		return listDataSet;
	}
	
	public NAryTree<String> getTreeMenu(final Context context) {
		
		Resources res = context.getResources();

		String[] continents = res.getStringArray(R.array.continents);
		
		CommandTreeNode<String> rootNode = createNode("Continents");
		
		for (final String continent : continents) {
			
			CommandTreeNode<String> continentNode = createNode(continent);
			
			int countriesArrayName = getResources().getIdentifier(formatValue(continent) + "_countries", "array", getPackageName());
			String[] countryList = res.getStringArray(countriesArrayName);
			
			for (final String country : countryList) {
				
				CommandTreeNode<String> countryNode = createNode(country);
				
				int citiesArrayName = res.getIdentifier(formatValue(country) + "_cities", "array", getPackageName());
				String[] cityList = res.getStringArray(citiesArrayName);
				
				for (final String city : cityList) {
					CommandTreeNode<String> cityNode = createNode(city);
					countryNode.addChild(cityNode);
				}
				
				continentNode.addChild(countryNode);
			}
			
			rootNode.addChild(continentNode);
		}
		
		
		// Create the initial representation of the tree data structure
		NAryTree<String> tree = new NAryTree<String>(rootNode);
		
		return tree;
	}
	
	public CommandTreeNode<String> createNode(final String nodeData) {
		
		CommandTreeNode<String> node = new CommandTreeNode<String>(nodeData);
		node.setCommand(new Command() {
			@Override
			public void executeCommand() {
				Toast.makeText(MainActivity.this, nodeData, Toast.LENGTH_SHORT).show();
			}
		});
		return node;
	}
	
	public String formatValue(String value) {
		return value.toLowerCase(Locale.getDefault()).replace(" ", "_");
	}
	
	@Override
	public void onBackPressed() {
		if (listAdapter.goBackOnNavigationHistory() == false) {
			super.onBackPressed();	
		}
	}
}
