using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Json;
using System.Text;
using System;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net.Http.Headers;
using Newtonsoft.Json;
using System.Reflection;

namespace ApiTest
{
    public class RestTest
    {
        private const string URL = "http://localhost:8080/project";
        private readonly HttpClient httpClient = new HttpClient();

        private async Task<T> Execute<T>(Func<Task<T>> func)
        {
            try
            {
                return await func();
            }
            catch (HttpRequestException ex) when (ex.StatusCode >= System.Net.HttpStatusCode.BadRequest && ex.StatusCode < System.Net.HttpStatusCode.InternalServerError)
            {
                throw new Exception(ex.Message);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public async Task<Excursie> Create(Excursie excursie)
        {
            return await Execute(async () =>
            {
                HttpResponseMessage response = await httpClient.PostAsJsonAsync(URL, excursie);
                response.EnsureSuccessStatusCode();
                string responseBody = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<Excursie>(responseBody);
            });
        }


    }
}
