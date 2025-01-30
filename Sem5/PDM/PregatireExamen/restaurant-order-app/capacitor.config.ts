import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  "appId": "io.ionic.restaurant.order",
  "appName": "Restaurant Order App",
  "webDir": "build",
  "plugins": {
    "Preferences": {
      "persist": true
    }
  }
};

export default config;
