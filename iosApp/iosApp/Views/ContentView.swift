import SwiftUI
import shared

struct ContentView: View {
    @State private var searchText: String = ""
    @State private var activeScreen: Int = 0
    @State private var isToolbarVisible = true
    let screens: [Screen] = [
        Screen(name: "Stock", iconSystemName: "chart.bar",screen: AnyView(StockScreen())),
        Screen(name: "Home", iconSystemName: "house",screen: AnyView(HomeView()))
    ]
    
    var body: some View {
        NavigationStack{
            TabView(selection: $activeScreen) {
                ForEach(screens.indices, id: \.self) { index in
                    screens[index].screen.tabItem {
                        Image(systemName: screens[index].iconSystemName)
                        Text(screens[index].name)
                    }.tag(index)
                }
                .safeAreaInset(edge: .top){
                    ExpandableNavigationBar("abc")
                }
            }
            .toolbar(.hidden, for: .navigationBar)
        }
        
    }
    
    @ViewBuilder
    func ExpandableNavigationBar(_ title:String) -> some View{
        VStack(spacing: 10){
            HStack{
                Text(title).font(.largeTitle.bold())
                Spacer()
                Image(systemName: "person.fill").font(.body)
            }
            HStack(spacing: 10){
                Image(systemName: "magnifyingglass").font(.body)
                TextField("Search stock, mutual funds etc", text:$searchText)
            }
            .padding(10)
            .frame(height: 45)
            .background{
                RoundedRectangle(cornerRadius: 20.0)
                    .fill(.bar)
            }
        }
        .padding(.top,25)
        .padding(.bottom,10)
    }
}
