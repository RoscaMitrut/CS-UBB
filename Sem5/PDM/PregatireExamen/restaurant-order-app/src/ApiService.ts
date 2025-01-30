import axios from 'axios';

export class ApiService {
  private static BASE_URL = 'http://localhost:3000';

  static async submitItem(item: { code: number, quantity: number, table: string }) {
    try {
      const response = await axios.post(`${this.BASE_URL}/item`, item);
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        throw error.response?.data || { text: 'Unknown error' };
      }
      throw { text: 'Network error' };
    }
  }

  static async submitOrder(items: any[], table: string) {
    const results = await Promise.allSettled(
      items
        .filter(item => item.quantity > 0)
        .map(item => this.submitItem({ 
          code: item.code, 
          quantity: item.quantity, 
          table 
        }))
    );

    return results.map((result, index) => ({
      item: items.filter(item => item.quantity > 0)[index],
      status: result.status,
      value: result.status === 'fulfilled' ? (result as PromiseFulfilledResult<any>).value : null,
      reason: result.status === 'rejected' ? (result as PromiseRejectedResult).reason : null
    }));
  }
}