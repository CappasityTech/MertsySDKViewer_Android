# Mertsy SDK for Android. 3D viewer sample.

Instruction:

1. Download mertsy-sdk.aar and save it in your "frameworks" folder.
2. To initialize the SDK, use your Mertsy SDK Access Token in the init method.

Kotlin:

```kotlin
  class App : Application() {

      override fun onCreate() {
          super.onCreate()
          MertsySDK.init(this, "YOUR TOKEN HERE")
      }
  }
```

Java:

```java
  public class App extends Application {

      @Override
      public void onCreate() {
          super.onCreate();
          MertsySDK.init(this, "YOUR TOKEN HERE");
      }
  }
```

# Contact us

SDK access: support@cappasity.com
Website - https://cappasity.com/contact/

Copyright (c) 2022-2023 Cappasity Inc. All rights reserved.

Neither the name of Cappasity Inc., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.



