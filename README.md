# SwipeLayout
android Custom Swipe Action for RecyclerView

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Sdk](https://img.shields.io/badge/sdk-19%2B-brightgreen.svg?style=plastic)](https://android-arsenal.com/api?level=19)

Feature
-----------------
RecyclerView Swipe Left & Swipe Right
custom design on Swipe View

How To
-----------------
How does another developer add this as a dependency?

STEP 1:  Add Below lines into project-level build.gradle:    

        allprojects {
          repositories {
            // ...
            maven { url 'https://jitpack.io' }

          }
        }
        
STEP 2: Reference the library itself in your module-level build.gradle:      

        implementation "com.github.SelfHitler:SwipeLayout:V1.0.1"

STEP 3: Must declare this Variable 

 ``Kotlin``

    val leftMenu = ArrayList<SwipeItemDetail>()
        
    val rightMenu = ArrayList<SwipeItemDetail>()
    
    val item = SwipeItemDetail()
    item.backgroundColor = "#678976" (Optional) // background Color for the single column  #Default Background Color White
    item.backgroundImage = ContextCompat.getDrawable(context, R.drawable.ic_heart).toBitmap() // (Optional)
    item.iconBitmap = ContextCompat.getDrawable(context, R.drawable.xxxxx).toBitmap()
    item.text = "left1" // (Optional)
    item.textColor = "#874567" (Optional) #default Text color BLACK
    item.textHeight = 40f (Optional) 
    
    item.iconWidth = 150 // to Resize Icon Image (Optional)
    item.iconHeight = 150 // (Optional)
    
      

STEP 4:

  ``Kotlin``
        
    val swipeController = SwipeController(object : SwipeControllerActions() {

            override fun onClicked(position: Int, state: SwipeState, swipeButtonPosition: Int) {
                when(state){
                    SwipeState.RIGHT->{
                        Log.e("clicked - ","${rightMenu[swipeButtonPosition].text}")
                    }
                    SwipeState.LEFT->{
                        Log.e("clicked - ","${leftMenu[swipeButtonPosition].text}")
                    }
                }
            }

        }, context, rightMenu, leftMenu)

        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                swipeController.onDraw(c)
            }
        })
        
    
## Reference:
  - [@SwipeControl](https://medium.com/@fanfatal/android-swipe-menu-with-recyclerview-8f28a235ff28) 
                      
## Author

👤 **Yabaze**

- FaceBook: [@MirakleYabaze](https://www.facebook.com/mirakle.yabaze)
- twitter: [@MirakleYabaze](https://twitter.com/mirakleyabaze)
- instagram: [@Yabaze1](https://www.instagram.com/yabaze1/)

License
-----------------

      Copyright (c) 2020-present, yabaze.t

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing,
      software distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.
