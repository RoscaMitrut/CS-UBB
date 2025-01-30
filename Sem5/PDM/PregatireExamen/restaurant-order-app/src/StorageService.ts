import { Preferences } from '@capacitor/preferences';

export class StorageService {
  static async set(key: string, value: string) {
    await Preferences.set({ key, value });
  }

  static async get(key: string): Promise<string | null> {
    const { value } = await Preferences.get({ key });
    return value;
  }

  static async remove(key: string) {
    await Preferences.remove({ key });
  }

  static async storeMenu(menu: any[]) {
    await this.set('menu', JSON.stringify(menu));
  }

  static async getMenu(): Promise<any[] | null> {
    const menuStr = await this.get('menu');
    return menuStr ? JSON.parse(menuStr) : null;
  }

  static async storeItems(items: any[]) {
    await this.set('orderItems', JSON.stringify(items));
  }

  static async getItems(): Promise<any[]> {
    const itemsStr = await this.get('orderItems');
    return itemsStr ? JSON.parse(itemsStr) : [];
  }
}