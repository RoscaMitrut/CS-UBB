export class WebSocketService {
  private static instance: WebSocketService;
  private ws: WebSocket | null = null;

  private constructor() {}

  public static getInstance(): WebSocketService {
    if (!this.instance) {
      this.instance = new WebSocketService();
    }
    return this.instance;
  }

  async connectAndFetchMenu(): Promise<any[]> {
    return new Promise((resolve, reject) => {
      // Use the WebSocket URL from the index.js server
      this.ws = new WebSocket('ws://localhost:3000');

      this.ws.onopen = () => {
        console.log('WebSocket connection established');
      };

      this.ws.onmessage = async (event) => {
        try {
          const menu = JSON.parse(event.data);
          resolve(menu);
        } catch (error) {
          reject(error);
        }
      };

      this.ws.onerror = (error) => {
        console.error('WebSocket error:', error);
        reject(error);
      };

      this.ws.onclose = () => {
        console.log('WebSocket connection closed');
      };
    });
  }
}