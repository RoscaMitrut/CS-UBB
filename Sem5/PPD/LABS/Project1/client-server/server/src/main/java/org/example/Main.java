package org.example;

import org.example.domain.*;
import org.example.domain.LinkedList;
import org.example.domain.Queue;
import org.example.request.Request;
import org.example.request.RequestType;
import org.example.response.Country;
import org.example.response.Response;
import org.example.response.ResponseType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import static org.example.utils.FileUtils.printListToFile;
import static org.example.utils.FileUtils.printMapToFile;


public class Main {

    private static final int readers = 4;     // p_r
    private static final int writers = 2;     // p_w
    private static final int dt = 1;     // deltaT
    private static final LinkedList resultList = new LinkedList();
    private static final int PORT = 50000;
    private static final int totalClients = 5;
    private static final MyList blackList = new MyList();
    private static final ExecutorService executor = Executors.newFixedThreadPool(readers);
    private static final AtomicInteger countriesLeft = new AtomicInteger(totalClients);
    private static final Queue queue = new Queue(countriesLeft);
    private static final Map<String, ReentrantLock> access = new HashMap<>();
    private static final Map<String, Boolean> finishedCountries = new HashMap<>();
    private static final AtomicInteger countriesFinalResultLeft = new AtomicInteger(totalClients);

    public static void main(String[] args) {

        for (int i = 0; i < 1000; ++i) {
            access.put(String.valueOf(i), new ReentrantLock());
        }

        Thread[] writersThreads = new Thread[writers];

        for (int i = 0; i < writers; ++i) {
            Thread thread = new Writer();
            writersThreads[i] = thread;
        }

        long programStart = System.currentTimeMillis();
        Arrays.stream(writersThreads).forEach(Thread::start);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started, listening on port " + PORT);

            while (countriesFinalResultLeft.get() != 0) {
                try {
                    final Socket clientSocket = serverSocket.accept();
                    executor.submit(new ClientHandler(clientSocket));
                    Thread.sleep(500);
                } catch (IOException | InterruptedException e) {
                    System.err.println("Exception caught when trying to listen on port " + PORT + " or listening for a connection");
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT);
            System.err.println(e.getMessage());
        }

        Arrays.stream(writersThreads).forEach(thread -> {
            try {
                thread.join();
                System.out.println("joined 1 writer thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        blackList.getList().forEach(item -> resultList.add(new Participant(item.id, -1, item.country)));

        resultList.sort();
        printListToFile(resultList, "clasament_parallel");

        Map<String, Integer> countryResult = new HashMap<>();
        resultList.getItemsAsList().forEach(participant -> {
            countryResult.merge(participant.getCountry(), participant.getScore(), Integer::sum);
        });

        printMapToFile(countryResult, "clasament_tari");
//        printMapToFile(countryResult, "clasament_valid");


        checkCompliance();
        long programEnd = System.currentTimeMillis();
        System.out.println(programEnd - programStart);
        // executor.shutdown();
    }

    public static class Writer extends Thread {
        @Override
        public void run() {
            while (countriesLeft.get() != 0 || !queue.isEmpty()) {
                Participant participant = null;
                try {
                    participant = queue.pop();
                } catch (InterruptedException ignored) {
                }

                if (participant == null) {
                    System.out.println("countries left is 0");
                    queue.finish();
                    continue;
                }

                access.get(participant.getId()).lock();

                if (!blackList.contains(new Pair(participant.getId(), participant.getCountry()))) {
                    if (participant.getScore() == -1) {
                        resultList.delete(participant);
                        blackList.add(new Pair(participant.getId(), participant.getCountry()));
                    } else {
                        Node actual = resultList.update(participant);

                        if (actual == null) {
                            resultList.add(participant);
                        }
                    }
                }

                access.get(participant.getId()).unlock();
            }
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream()); ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                Request request = (Request) in.readObject();

                try {
                    if (request.getRequestType() == RequestType.UPDATE_SCORE) {
                        var data = request.getData();
                        data.forEach(result -> {
                            try {
                                var id = result.getId();
                                var country = result.getCountry();
                                var score = result.getScore();
                                var participant = new Participant(String.valueOf(id), score, country);
                                queue.push(participant);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        out.writeObject(new Response(ResponseType.SUCCESS, null));
                        out.flush();

                    } else if (request.getRequestType() == RequestType.PARTIAL_SCORE) {
                        // calculate and send partial ranking
                        long start = 0, end = dt;

                        while (end - start >= dt) {
                            start = System.currentTimeMillis();
                            Future<Map<String, Integer>> partialFutureResult = executor.submit(() -> {
                                Map<String, Integer> result = new HashMap<>();
                                resultList.getItemsAsList().forEach(participant -> {
                                    result.merge(participant.getCountry(), participant.getScore(), Integer::sum);
                                });
                                return result;
                            });

                            Map<String, Integer> partialResult = partialFutureResult.get();
                            List<Country> partialResultList = new ArrayList<>();

                            for (Map.Entry<String, Integer> entry : partialResult.entrySet()) {
                                partialResultList.add(new Country(entry.getKey(), entry.getValue()));
                            }
                            end = System.currentTimeMillis();

                            if (end - start < dt) {
                                out.writeObject(new Response(ResponseType.SUCCESS, partialResultList));
                                out.flush();
                                break;
                            }
                        }
                    } else if (request.getRequestType() == RequestType.FINAL_SCORE) {
                        var country = request.getCountry();
                        if (finishedCountries.get(country) == null) {
                            countriesLeft.decrementAndGet();
                            finishedCountries.put(country, true);
                        }

                        if (countriesLeft.get() == 0) {
                            countriesFinalResultLeft.decrementAndGet();
                            Future<Map<String, Integer>> futureResult = executor.submit(() -> {
                                Map<String, Integer> result = new HashMap<>();
                                resultList.getItemsAsList().forEach(participant -> {
                                    result.merge(participant.getCountry(), participant.getScore(), Integer::sum);
                                });
                                return result;
                            });

                            Map<String, Integer> result = futureResult.get();
                            List<Country> resultList = new ArrayList<>();

                            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                                resultList.add(new Country(entry.getKey(), entry.getValue()));
                            }

                            out.writeObject(new Response(ResponseType.SUCCESS, resultList));
                            out.flush();
                            queue.finish();
                        } else {
                            out.writeObject(new Response(ResponseType.FAILURE, null));
                            out.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out.writeObject(new Response(ResponseType.FAILURE, null));
                    out.flush();
                }
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void checkCompliance() {
        java.util.LinkedList<Participant> valid = new java.util.LinkedList<>();

        try {
            File obj = new File("server/src/main/resources/clasament_valid");
            Scanner scanner = new Scanner(obj);

            while (scanner.hasNextLine()) {
                String[] rez = scanner.nextLine().split(",");
                valid.add(new Participant(rez[0], Integer.parseInt(rez[1]), ""));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Participant> result = resultList.getItemsAsList();

        if (valid.size() != result.size()) {
            throw new Error("The sizes of the two results differ");
        }

        for (int i = 0; i < result.size() - 1; ++i) {
            if (result.get(i).getScore() < result.get(i + 1).getScore()) {
                throw new Error("The array is not sorted in the right direction");
            }
        }

        for (Participant participant : result) {
            if (valid.stream().noneMatch(item -> item.getId().equals(participant.getId()) && item.getScore() == participant.getScore())) {
                throw new Error(String.format("Element with id %s and score %d and country %s exists in the calculated result but doesn't in the valid result", participant.getId(), participant.getScore(), participant.getCountry()));
            }
        }

        System.out.println("The result is compliant");
    }
}
