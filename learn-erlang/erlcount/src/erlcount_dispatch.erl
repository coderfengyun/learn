%%%-------------------------------------------------------------------
%%% @author yangdoudou
%%% @copyright (C) 2017, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 12. 十一月 2017 下午1:38
%%%-------------------------------------------------------------------
%% API
-module(erlcount_dispatch).
-behaviour(gen_fsm).
-export([start_link/0, complete/4]).
-export([init/1, dispatching/2, listening/2, handle_event/3,
  handle_sync_event/4, handle_info/3, terminate/3, code_change/4]).

-define(POOL, erlcount).
-record(data, {regex=[], refs=[]}).

start_link() ->
  gen_fsm:start_link(?MODULE, [], []).

complete(Pid, Regex, Ref, Count) ->
  gen_fsm:send_all_state_event(Pid, Regex, Ref, Count).

init(_Arg0) ->
  {ok, Re} = application:get_env(regex),
  {ok, Dir} = application:get_env(directory),
  {ok, MaxFiles} = application:get_env(max_files),
  ppool:start_pool(?POOL, MaxFiles, {erlcount_counter, start_link, []}),
  case lists:all(fun valid_regex/1, Re) of
      true ->
        self() ! {start, Dir},
        {ok, dispatching, #data{regex = [{R, 0} || R <- Re]}};
      false ->
        {stop, invalid_regex}
  end.

valid_regex(Re) ->
  try re:run("", Re) of
    _ -> true
  catch
      error:badarg  -> false
  end.

handle_sync_event(Event, _From, State, Data) ->
  io:format("Unexpected event: ~p~n", [Event]),
  {next_state, State, Data}.

handle_info({start, Dir}, State, Data) ->
  gen_fsm:send_event(self(), erlcount_lib:find_erl(Dir)),
  {next_state, State, Data}.

handle_event({complete, Regex, Ref, Count}, State, Data = #data{regex= AllRes, refs = Refs}) ->
  {Regex, OldCount} = lists:keyfind(Regex, 1, AllRes),
  NewRe = lists:keyreplace(Regex, 1, AllRes, {Regex, OldCount + Count}),
  NewData = Data#data{regex = NewRe, refs = Refs -- [Ref]},
  case State of
    dispatching ->  {next_state, dispatching, NewData};
    listening -> listening(done, NewData)
  end.

dispatching({continue, FileName, DeQueue_And_Run_CallBack}, Data = #data{regex = Re, refs = Refs}) ->
  F = fun({Regex, _Count}, NewRefs) ->
    Ref = make_ref(),
    ppool:aync_queue(?POOL, [self(), Ref, FileName, Regex]),
    [Ref|NewRefs]
  end,

  NewRefs = lists:foldl(F, Refs, Re),
  gen_fsm:send_event(self(), DeQueue_And_Run_CallBack),
  {next_state, dispatching, Data#data{refs = NewRefs}};
dispatching(done, Data) ->
  listening(done, Data).

listening(done, #data{regex = Regex, refs = []}) ->
  [io:format("Regex ~s has ~p results~n", [R, C]) || {R, C} <- Regex],
  {stop, normal, done};
listening(done, Data) ->
  {next_state, listening, Data}.

terminate(_Reason, _State, _Data) ->
  ok.

code_change(_OldVersion, State, Data, _Extra) ->
  {ok,  State, Data}.