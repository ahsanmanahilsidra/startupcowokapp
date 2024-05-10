package Fragments

import Adopters.Myadapter
import Models.Space
import Models.user
import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale


class Home : Fragment(),NavigationView.OnNavigationItemSelectedListener{


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var bindingFragment: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onStart() {
        super.onStart()
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("user")
        usersCollection.document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener { querySnapshot ->

                if (querySnapshot != null) {
                    if (querySnapshot.data!!["image"] != null) {

                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        bindingFragment = FragmentHomeBinding.inflate(inflater, container, false)
        bindingFragment.viewpager.adapter = Myadapter(this)
        TabLayoutMediator(bindingFragment.Tablayout, bindingFragment.viewpager) { tab, Position ->
            when (Position) {
                0 -> {
                    tab.text="Spaces"
                    tab.setIcon(R.drawable.spaceselected)

                }
                1 -> {tab.text = "Events"
                    tab.setIcon(R.drawable.eventselected)
                }
                2 -> {
                    tab.text = "Posts"
                    tab.setIcon(R.drawable.postselected)
                }
            }
        }.attach()


//        val navigationView = activity?.findViewById<NavigationView>(R.id.navigationView)
//        navigationView?.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.Showbookings -> {
//                    // Handle click on "Home" item
//                    Toast.makeText(requireContext(), "Home clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.Markattandece -> {
//                    // Handle click on "Profile" item
//                    Toast.makeText(requireContext(), "Profile clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.changeemail -> {
//                    // Handle click on "Profile" item
//                    Toast.makeText(requireContext(), "Profile clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.changepassword-> {
//                    // Handle click on "Profile" item
//                    Toast.makeText(requireContext(), "Profile clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.Log_Out -> {
//                    // Handle click on "Profile" item
//                    Toast.makeText(requireContext(), "Profile clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                // Add more cases for other menu items as needed
//                else -> false
//            }
//        }
        val searchView=bindingFragment.searchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)

                return true
            }

        })
      askNotificationPermission()
        return bindingFragment.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up the toolbar
//        (activity as AppCompatActivity).setSupportActionBar(bindingFragment.toolbare)
//
//        // Set up the drawer toggle
//        val drawerToggle = ActionBarDrawerToggle(
//            requireActivity(),
//            bindingFragment.drawerlayout,
//            bindingFragment.toolbare,
//            R.string.open_nav,
//            R.string.close_nav
//        )
//        bindingFragment.drawerlayout.addDrawerListener(drawerToggle)
//        drawerToggle.syncState()
//        bindingFragment.navigationView.setNavigationItemSelectedListener(this)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            // Permission already granted, start location updates
            startLocationUpdates()
        }

        bindingFragment.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Here you can handle the selected fragment position
                when (position) {
                    0 -> {
                        bindingFragment.head.setText("Spaces")
                    }
                    1 -> {
                        bindingFragment.head.setText("Events")
                    }
                    2 ->{
                        bindingFragment.head.setText("Posts")
                    }
                    // Add more cases as needed
                }
            }
        })
    }

    companion object {

            private const val REQUEST_LOCATION_PERMISSION = 100

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Showbookings->Toast.makeText(context,"yes",Toast.LENGTH_SHORT).show()
            R.id.Markattandece->Toast.makeText(context,"yes",Toast.LENGTH_SHORT).show()
            R.id.changeemail->Toast.makeText(context,"yes",Toast.LENGTH_SHORT).show()
            R.id.changepassword->Toast.makeText(context,"yes",Toast.LENGTH_SHORT).show()
            R.id.Log_Out->Toast.makeText(context,"yes",Toast.LENGTH_SHORT).show()
        }
//        bindingFragment.drawerlayout.closeDrawer(GravityCompat.START)
        return true
    }
    fun filterList(query:String?){
        val space = requireActivity().supportFragmentManager.findFragmentByTag("Space") as Fragments.Space?
        var spaceList=  space?.spaceList
        var adapter=space?.adapter
        val filterList= ArrayList<Space>()
        if(query!=null){
            if (spaceList != null) {
                for (i in spaceList){
                    if (i.Spacetitle?.lowercase(Locale.ROOT)!!.contains(query))
                        filterList.add(i)
                }
            }
            if(filterList.isEmpty()){
                Toast.makeText(context,"no data found", Toast.LENGTH_SHORT).show()
            }
            else{
                adapter?.setfilterList(filterList)
            }
        }

    }
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // Got last known location. In some rare situations, this can be null.
                if (location != null) {
                    // Reverse geocode to get the city name
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses: List<Address>? =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    addresses?.let {
                        if (it.isNotEmpty()) {
                            val cityName = it[0].locality
                            bindingFragment.location.text = "Location  $cityName"
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                // Handle failure
                Toast.makeText(
                    requireContext(),
                    "Failed to get location: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

}
